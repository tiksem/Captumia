package com.captumia.ui.forms;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.captumia.R;
import com.captumia.app.CaptumiaApplication;
import com.captumia.app.NetworkHandler;
import com.captumia.data.PackageSubscription;
import com.captumia.network.PackagesLazyLoadingList;
import com.captumia.network.RestApiClient;
import com.captumia.ui.BaseLazyLoadingFragment;
import com.captumia.ui.UiUtils;
import com.captumia.ui.adapters.PackagesAdapter;
import com.utils.framework.collections.LazyLoadingList;
import com.utilsframework.android.adapters.ViewArrayAdapter;
import com.utilsframework.android.navdrawer.ActionBarTitleProvider;
import com.utilsframework.android.network.retrofit.RetrofitRequestManager;
import com.utilsframework.android.view.listview.ListViews;

import okhttp3.Cookie;

public class PackagesListFragment extends BaseLazyLoadingFragment<Object>
        implements ActionBarTitleProvider {

    private ServiceFromFragment serviceFromFragment;
    private PackagesAdapter packagesAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = (ListView) getListView();
        View header = ListViews.addHeader(listView, R.layout.add_service_header);
        UiUtils.setupHeaderBackground(header);
    }

    @Override
    protected void onListItemClicked(Object item, int position) {
        onPackageSelected((PackageSubscription) item);
    }

    private void onPackageSelected(PackageSubscription packageSubscription) {
        getActivityInterface().setSelectedPackage(packageSubscription.getId(),
                packageSubscription.isUserPackage());
        if (serviceFromFragment == null) {
            serviceFromFragment = new ServiceFromFragment();
        }
        getNavigationInterface().replaceFragment(serviceFromFragment, 1);
        packagesAdapter.setSelectedPackageId(packageSubscription.getId(),
                packageSubscription.isUserPackage());
    }

    @Override
    protected ViewArrayAdapter<Object, ?> createAdapter() {
        packagesAdapter = new PackagesAdapter(getContext());
        return packagesAdapter;
    }

    @Override
    protected LazyLoadingList<Object> getLazyLoadingList(RetrofitRequestManager requestManager,
                                                          String filter) {
        return new PackagesLazyLoadingList(requestManager, getRestApiClient());
    }

    @Override
    public String getActionBarTitle() {
        return getString(R.string.packages_title);
    }

    public AddServiceActivityInterface getActivityInterface() {
        return (AddServiceActivityInterface) getActivity();
    }
}
