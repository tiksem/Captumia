package com.captumia.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.captumia.R;
import com.captumia.data.Category;
import com.captumia.data.Region;
import com.captumia.data.Tag;
import com.captumia.ui.adapters.OperatingHoursAdapter;
import com.captumia.ui.adapters.SelectCategoryAdapter;
import com.captumia.ui.adapters.SelectRegionAdapter;
import com.utilsframework.android.network.retrofit.CallProvider;
import com.utilsframework.android.view.GuiUtilities;
import com.utilsframework.android.view.scrollview.ScrollViews;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class AddPostActivity extends PageLoadingActivity {
    private Spinner regionsSpinner;
    private Spinner categorySpinner;
    private List<Spinner> operatingHoursSpinners;

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
}
