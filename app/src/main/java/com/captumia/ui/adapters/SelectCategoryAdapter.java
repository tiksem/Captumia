package com.captumia.ui.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.captumia.R;
import com.captumia.data.Category;
import com.utilsframework.android.adapters.SingleViewArrayAdapter;
import com.utilsframework.android.adapters.SpinnerAdapter;
import com.utilsframework.android.view.GuiUtilities;
import com.utilsframework.android.view.MeasureUtils;

public class SelectCategoryAdapter extends BaseSpinnerAdapter<Category> {
    public SelectCategoryAdapter(Context context) {
        super(context);
    }

    @Override
    protected void reuseView(Category category, TextView textView, int position, View view) {
        super.reuseView(category, textView, position, view);
        if (category != null && category.getParent() != 0) {
            textView.setText(" - " + textView.getText());
        }
    }

    @Override
    public int getHint() {
        return R.string.select_service_category;
    }

    @Override
    public String getTextOfElement(Category category) {
        return category.getName();
    }
}
