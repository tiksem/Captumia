package com.captumia.network;

import com.captumia.data.PackageSubscription;
import com.utils.framework.Lists;
import com.utils.framework.Primitive;
import com.utilsframework.android.network.MultipleCallsOnePageRetrofitLazyLoadingList;
import com.utilsframework.android.network.retrofit.RetrofitRequestManager;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;

public class PackagesLazyLoadingList extends
        MultipleCallsOnePageRetrofitLazyLoadingList<Object, PackageSubscription> {
    private RestApiClient restApiClient;

    public PackagesLazyLoadingList(RetrofitRequestManager requestManager, RestApiClient restApiClient) {
        super(requestManager);
        this.restApiClient = restApiClient;
    }

    @Override
    protected List<Call<List<PackageSubscription>>> createCalls() {
        return Arrays.asList(restApiClient.getUserPackages(), restApiClient.getPackages());
    }

    @Override
    protected Collection<?> transform(List<PackageSubscription> list) {
        Collections.sort(list, new Comparator<PackageSubscription>() {
            @Override
            public int compare(PackageSubscription lhs, PackageSubscription rhs) {
                return Primitive.compare(rhs.getPrice(), lhs.getPrice());
            }
        });

        boolean isFirstPage = getLoadedElementsCount() == 0;
        if (isFirstPage) {
            for (PackageSubscription subscription : list) {
                subscription.setIsUserPackage(true);
            }
        }

        Object header = isFirstPage ? PackageSubscription.YOUR_PACKAGES_HEADER
                : PackageSubscription.PURCHASE_PACKAGES_HEADER;
        return Lists.listWithAddingElementToFront((List)list, header);
    }
}
