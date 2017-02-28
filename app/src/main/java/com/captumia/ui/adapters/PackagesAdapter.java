package com.captumia.ui.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import com.captumia.R;
import com.captumia.data.PackageSubscription;
import com.captumia.ui.adapters.holders.PackageHolder;
import com.utilsframework.android.adapters.navigation.LazyLoadingListAdapter;

public class PackagesAdapter extends LazyLoadingListAdapter<Object, PackageHolder> {
    private int selectedPackageId = -1;
    private boolean isUserPackageSelected = false;

    protected static final int VIEW_TYPES_COUNT = LazyLoadingListAdapter.VIEW_TYPES_COUNT + 2;
    private static final int YOUR_PACKAGES_HEADER_VIEW_TYPE = LazyLoadingListAdapter.VIEW_TYPES_COUNT;
    private static final int PURCHASE_PACKAGES_HEADER_VIEW_TYPE = LazyLoadingListAdapter.VIEW_TYPES_COUNT + 1;

    public PackagesAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        Object element = getElement(position);
        if (element == PackageSubscription.YOUR_PACKAGES_HEADER) {
            return YOUR_PACKAGES_HEADER_VIEW_TYPE;
        } else if(element == PackageSubscription.PURCHASE_PACKAGES_HEADER) {
            return PURCHASE_PACKAGES_HEADER_VIEW_TYPE;
        }

        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPES_COUNT;
    }

    @Override
    protected int getRootLayoutId(int viewType) {
        if (viewType == YOUR_PACKAGES_HEADER_VIEW_TYPE) {
            return R.layout.your_packages_header;
        } else if (viewType == PURCHASE_PACKAGES_HEADER_VIEW_TYPE) {
            return R.layout.purchase_packages_header;
        }

        return R.layout.package_item;
    }

    @Override
    protected PackageHolder createViewHolder(View view, int itemViewType) {
        if (itemViewType == NORMAL_VIEW_TYPE) {
            return new PackageHolder(view);
        }

        return null;
    }

    public void setSelectedPackageId(int selectedPackageId, boolean isUserPackage) {
        this.selectedPackageId = selectedPackageId;
        this.isUserPackageSelected = isUserPackage;
        notifyDataSetChanged();
    }

    @Override
    protected void reuseView(Object object, PackageHolder holder,
                             int position, View view) {
        if (object instanceof PackageSubscription) {
            PackageSubscription subscription = (PackageSubscription) object;
            holder.title.setText(subscription.getTitle());

            Resources resources = view.getResources();
            int limit = subscription.getLimit();
            String description;
            if (limit != 0) {
                description = resources.getString(R.string.limited_package_description,
                        subscription.getPrice(), limit);
            } else {
                description = resources.getString(R.string.unlimited_package_description,
                        subscription.getPrice(), subscription.getDuration());
            }
            holder.description.setText(description);

            holder.selection.setSelected(
                    subscription.getId() == selectedPackageId &&
                            isUserPackageSelected == subscription.isUserPackage());
        }
    }

    @Override
    public boolean isEnabled(int position) {
        return getItemViewType(position) == NORMAL_VIEW_TYPE;
    }
}
