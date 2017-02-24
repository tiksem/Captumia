package com.captumia.ui.forms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.captumia.R;
import com.captumia.data.Category;
import com.captumia.data.Region;
import com.captumia.data.Tag;
import com.captumia.ui.PageLoadingActivity;
import com.captumia.ui.UiUtils;
import com.captumia.ui.adapters.OperatingHoursAdapter;
import com.captumia.ui.adapters.SelectCategoryAdapter;
import com.captumia.ui.adapters.SelectRegionAdapter;
import com.squareup.picasso.Picasso;
import com.utilsframework.android.network.retrofit.CallProvider;
import com.utilsframework.android.view.GuiUtilities;
import com.utilsframework.android.view.Toasts;
import com.utilsframework.android.view.scrollview.ScrollViews;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;

public class AddPostActivity extends PageLoadingActivity implements EasyImage.Callbacks {
    public static final int COVER_IMAGE_PICK = 0;
    private Spinner regionsSpinner;
    private Spinner categorySpinner;
    private List<Spinner> operatingHoursSpinners;
    private ImageView coverImageView;
    private View coverImageContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        regionsSpinner = (Spinner) findViewById(R.id.region);
        categorySpinner = (Spinner) findViewById(R.id.category);

        final View operationHoursView = findViewById(R.id.operation_hours);
        findViewById(R.id.operation_hours_toggle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (operationHoursView.getVisibility() == View.GONE) {
                    operationHoursView.setVisibility(View.VISIBLE);
                    ScrollViews.scrollToView(getPageContentView(), operationHoursView);
                } else {
                    operationHoursView.setVisibility(View.GONE);
                }

            }
        });

        operatingHoursSpinners = GuiUtilities.getAllChildrenRecursive(operationHoursView,
                Spinner.class);

        for (Spinner spinner : operatingHoursSpinners) {
            spinner.setAdapter(new OperatingHoursAdapter(this));
        }

        coverImageView = (ImageView) findViewById(R.id.cover_image);
        View coverImageSelector = findViewById(R.id.select_cover_image);
        coverImageSelector.setOnClickListener(imagePickListener());
        coverImageView.setOnClickListener(imagePickListener());

        coverImageContainer = findViewById(R.id.cover_image_container);

        findViewById(R.id.delete_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteCoverImage();
            }
        });
    }

    private void onDeleteCoverImage() {
        coverImageContainer.setVisibility(View.GONE);
        coverImageView.setImageDrawable(null);
    }

    private View.OnClickListener imagePickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickCoverImage();
            }
        };
    }

    private void pickCoverImage() {
        EasyImage.configuration(this).setAllowMultiplePickInGallery(false);
        EasyImage.openGallery(this, COVER_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, this);
    }

    @Override
    public List<CallProvider> getCallProviders() {
        List<CallProvider> callProviders = new ArrayList<>();
        callProviders.add(new CallProvider<List<Region>>() {
            @Override
            public Call<List<Region>> getCall() {
                return getRestApiClient().getRegions();
            }

            @Override
            public void onSuccess(List<Region> result) {
                onRegionsLoaded(result);
            }
        });
        callProviders.add(new CallProvider<List<Category>>() {
            @Override
            public Call<List<Category>> getCall() {
                return getRestApiClient().getCategories();
            }

            @Override
            public void onSuccess(List<Category> result) {
                onCategoriesLoaded(result);
            }
        });
        callProviders.add(new CallProvider<List<Tag>>() {
            @Override
            public Call<List<Tag>> getCall() {
                return getRestApiClient().getTags();
            }

            @Override
            public void onSuccess(List<Tag> result) {
                onTagsLoaded(result);
            }
        });
        return callProviders;
    }

    private void onTagsLoaded(List<Tag> result) {
        ViewGroup tagsView = (ViewGroup) findViewById(R.id.tags);
        for (Tag tag : result) {
            CheckBox tagView = (CheckBox) View.inflate(this, R.layout.add_post_tag, null);
            tagView.setText(tag.getName());
            tagsView.addView(tagView);
        }
    }

    private void onCategoriesLoaded(List<Category> result) {
        SelectCategoryAdapter adapter = new SelectCategoryAdapter(this);
        adapter.setElements(result);
        categorySpinner.setAdapter(adapter);
    }

    private void onRegionsLoaded(List<Region> result) {
        SelectRegionAdapter adapter = new SelectRegionAdapter(this);
        adapter.setElements(result);
        regionsSpinner.setAdapter(adapter);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, AddPostActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.add_post_activity;
    }

    @Override
    public ScrollView getPageContentView() {
        return (ScrollView) super.getPageContentView();
    }

    @Override
    public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
        Toasts.toast(this, R.string.image_pick_failed);
    }

    @Override
    public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source,
                               int type) {
        if (type == COVER_IMAGE_PICK) {
            final File file = imageFiles.get(0);
            if (coverImageContainer.getVisibility() == View.GONE) {
                coverImageView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getPageContentView().smoothScrollBy(0,
                                coverImageView.getMeasuredHeight());
                    }
                }, 30);
            }

            coverImageContainer.setVisibility(View.VISIBLE);
            UiUtils.loadImageWithCenterCrop(Picasso.with(this).load(file), coverImageView);
        }
    }

    @Override
    public void onCanceled(EasyImage.ImageSource source, int type) {

    }
}
