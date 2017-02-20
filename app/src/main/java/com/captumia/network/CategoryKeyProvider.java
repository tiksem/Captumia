package com.captumia.network;

import com.captumia.data.Category;
import com.utils.framework.KeyProvider;

public class CategoryKeyProvider implements KeyProvider<Object, Category> {
    @Override
    public Object getKey(Category value) {
        return value.getId();
    }
}
