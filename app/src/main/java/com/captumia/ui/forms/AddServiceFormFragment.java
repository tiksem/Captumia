package com.captumia.ui.forms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.captumia.R;
import com.captumia.data.Category;
import com.captumia.data.Media;
import com.captumia.data.OperatingHoursItem;
import com.captumia.data.Post;
import com.captumia.data.Region;
import com.captumia.data.Tag;
import com.captumia.ui.BasePageLoadingFragment;
import com.captumia.ui.UiUtils;
import com.captumia.ui.adapters.AddPostPhotoGalleryAdapter;
import com.captumia.ui.adapters.OperatingHoursAdapter;
import com.captumia.ui.adapters.SelectCategoryAdapter;
import com.captumia.ui.adapters.SelectRegionAdapter;
import com.captumia.ui.content.ServiceFragment;
import com.squareup.picasso.Picasso;
import com.utils.framework.CollectionUtils;
import com.utils.framework.Transformer;
import com.utilsframework.android.navdrawer.ActionBarTitleProvider;
import com.utilsframework.android.network.retrofit.CallProvider;
import com.utilsframework.android.view.GuiUtilities;
import com.utilsframework.android.view.Toasts;
import com.utilsframework.android.view.scrollview.ScrollViews;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;

public class AddServiceFormFragment extends BasePageLoadingFragment implements EasyImage.Callbacks,
        ActionBarTitleProvider {
    private static final int COVER_IMAGE_PICK = 0;
    private static final int GALLERY_IMAGE_PICK = 1;

    @BindView(R.id.region)
    Spinner regionsSpinner;
    @BindView(R.id.category)
    Spinner categorySpinner;
    private List<Spinner> operatingHoursSpinners;
    ImageView coverImageView;
    @BindView(R.id.cover_image_container)
    View coverImageContainer;
    AddPostPhotoGalleryAdapter photoGalleryAdapter;
    @BindView(R.id.description)
    EditText descriptionView;
    @BindView(R.id.phone)
    EditText phoneView;
    @BindView(R.id.location)
    EditText locationView;
    @BindView(R.id.service_name)
    EditText nameView;
    @BindView(R.id.email)
    EditText emailView;
    @BindView(R.id.video)
    EditText videoUrlView;
    @BindView(R.id.website)
    EditText webSiteView;
    private List<CheckBox> tags;
    private File coverImage;
    private SelectCategoryAdapter categoryAdapter;
    private SelectRegionAdapter regionAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        setupOperationHoursViews(view);
        setupCoverImage(view);

        if (photoGalleryAdapter == null) {
            photoGalleryAdapter = new AddPostPhotoGalleryAdapter(getContext());
            photoGalleryAdapter.setElements(new ArrayList<File>());
        }
        AbsListView photoGalleryView = (AbsListView) view.findViewById(R.id.photo_gallery_list);
        photoGalleryView.setAdapter(photoGalleryAdapter);

        UiUtils.setupHeaderBackground(view);
    }

    @OnClick(R.id.preview)
    void openPreview() {
        Post post = new Post();
        String name = nameView.getText().toString();
        if (name.isEmpty()) {
            Toasts.toast(getContext(), R.string.empty_name);
            return;
        }
        post.setTitle(name);

        String email = emailView.getText().toString();
        if (email.isEmpty()) {
            Toasts.toast(getContext(), R.string.empty_email);
            return;
        }
        post.setEmail(email);

        String description = descriptionView.getText().toString();
        if (description.isEmpty()) {
            Toasts.toast(getContext(), R.string.empty_description);
            return;
        }

        post.setDescription(description);
        post.setPhone(phoneView.getText().toString());

        String location = locationView.getText().toString();
        if (!location.isEmpty()) {
            post.setLocation(location);
        }
        int regionPosition = regionsSpinner.getSelectedItemPosition();
        if (regionPosition != 0) {
            Region region = regionAdapter.getElement(regionPosition);
            post.setRegion(region);
        } else {
            Toasts.toast(getContext(), R.string.select_service_region);
            return;
        }

        if (coverImage != null) {
            post.setMedia(new Media(coverImage.getAbsolutePath()));
        }

        int categoryPosition = categorySpinner.getSelectedItemPosition();
        if (categoryPosition == 0) {
            Toasts.toast(getContext(), R.string.select_service_category);
            return;
        }
        post.setCategory(categoryAdapter.getElement(categoryPosition));

        post.setTags(getSelectedTags());
        post.setVideoUrl(videoUrlView.getText().toString());
        post.setWebsite(webSiteView.getText().toString());
        post.setPhone(phoneView.getText().toString());

        List<OperatingHoursItem> operatingHours = new ArrayList<>();
        for (int i = 0; i < operatingHoursSpinners.size(); i+=2) {
            Spinner start = operatingHoursSpinners.get(i);
            Spinner end = operatingHoursSpinners.get(i + 1);
            if (start.getSelectedItemPosition() == 0 || end.getSelectedItemPosition() == 0) {
                continue;
            }
            OperatingHoursItem hoursItem = new OperatingHoursItem();
            hoursItem.setDay(i / 2);
            hoursItem.setStartTime(start.getSelectedItem().toString());
            hoursItem.setEndTime(end.getSelectedItem().toString());
            operatingHours.add(hoursItem);
        }
        post.setOperationHours(operatingHours);

        List<String> photos = CollectionUtils.transform(photoGalleryAdapter.getElements(),
                new Transformer<File, String>() {
                    @Override
                    public String get(File file) {
                        return file.getAbsolutePath();
                    }
                });
        post.setPhotos(photos);

        ServiceFragment serviceFragment = ServiceFragment.create(post, true);
        getNavigationInterface().replaceFragment(serviceFragment, 1);
    }

    @OnClick(R.id.add_gallery_image)
    void pickGalleryImage() {
        EasyImage.configuration(getContext()).setAllowMultiplePickInGallery(true);
        EasyImage.openGallery(this, GALLERY_IMAGE_PICK);
    }

    private void setupCoverImage(View view) {
        coverImageView = (ImageView) view.findViewById(R.id.cover_image);
        coverImageContainer = view.findViewById(R.id.cover_image_container);

        view.findViewById(R.id.delete_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteCoverImage();
            }
        });
    }

    private void setupOperationHoursViews(View view) {
        final View operationHoursView = view.findViewById(R.id.operation_hours);
        view.findViewById(R.id.operation_hours_toggle).setOnClickListener(new View.OnClickListener() {
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
            spinner.setAdapter(new OperatingHoursAdapter(getContext()));
        }
    }

    private void onDeleteCoverImage() {
        coverImageContainer.setVisibility(View.GONE);
        coverImageView.setImageDrawable(null);
        coverImage = null;
    }

    @OnClick({R.id.cover_image, R.id.select_cover_image})
    void pickCoverImage() {
        EasyImage.configuration(getContext()).setAllowMultiplePickInGallery(false);
        EasyImage.openGallery(this, COVER_IMAGE_PICK);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), this);
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
        tags = new ArrayList<>();
        ViewGroup tagsView = (ViewGroup) getView().findViewById(R.id.tags);
        for (Tag tag : result) {
            CheckBox tagView = (CheckBox) View.inflate(getContext(), R.layout.add_post_tag, null);
            tagView.setTag(tag);
            tagView.setText(tag.getName());
            tagsView.addView(tagView);
            tags.add(tagView);
        }
    }

    private List<Tag> getSelectedTags() {
        return CollectionUtils.transformIgnoreNulls(tags, new Transformer<CheckBox, Tag>() {
            @Override
            public Tag get(CheckBox checkBox) {
                if (checkBox.isChecked()) {
                    return (Tag) checkBox.getTag();
                }

                return null;
            }
        });
    }

    private void onCategoriesLoaded(List<Category> result) {
        categoryAdapter = new SelectCategoryAdapter(getContext());
        categoryAdapter.setElements(result);
        categorySpinner.setAdapter(categoryAdapter);
    }

    private void onRegionsLoaded(List<Region> result) {
        regionAdapter = new SelectRegionAdapter(getContext());
        regionAdapter.setElements(result);
        regionsSpinner.setAdapter(regionAdapter);
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.post_form_fragment;
    }

    @Override
    public ScrollView getPageContentView() {
        return (ScrollView) super.getPageContentView();
    }

    @Override
    public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
        Toasts.toast(getContext(), R.string.image_pick_failed);
        Log.e(EasyImage.class.getSimpleName(), "image pick failed", e);
    }

    @Override
    public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source,
                               int type) {
        if (type == COVER_IMAGE_PICK) {
            coverImage = imageFiles.get(0);
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
            UiUtils.loadImageWithCenterCrop(Picasso.with(getContext()).load(coverImage), coverImageView);
        } else if(type == GALLERY_IMAGE_PICK) {
            photoGalleryAdapter.addElements(imageFiles);
        }
    }

    @Override
    public String getActionBarTitle() {
        return getString(R.string.create_service);
    }

    @Override
    public void onCanceled(EasyImage.ImageSource source, int type) {

    }

    public AddServiceActivityInterface getActivityInterface() {
        return (AddServiceActivityInterface) getActivity();
    }
}
