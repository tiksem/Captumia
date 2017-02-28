package com.captumia.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;
import com.utils.framework.strings.Strings;

public class BasePost implements Parcelable {
    private int id;
    @JsonIgnore
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonSetter("title")
    public void setTitleFromJson(JsonNode jsonNode) {
        title = jsonNode.get("rendered").asText();
    }

    protected final JsonNode getFieldItem(JsonNode fields, String key) {
        JsonNode array = fields.get(key);
        if (array != null && array.size() > 0) {
            JsonNode value = array.get(0);
            if (value == null) {
                return null;
            }

            return value;
        }

        return null;
    }

    protected final String getStringField(JsonNode fields, String key) {
        JsonNode node = getFieldItem(fields, key);
        if (node == null) {
            return null;
        }

        String text = node.asText();
        if (Strings.isEmpty(text)) {
            return null;
        }

        return text;
    }

    protected final int getIntField(JsonNode fields, String key, int defaultValue) {
        JsonNode node = getFieldItem(fields, key);
        if (node == null) {
            return defaultValue;
        }

        if (node.isTextual()) {
            String asText = node.asText();
            if (Strings.isEmpty(asText)) {
                return defaultValue;
            }

            return Integer.valueOf(asText);
        }

        return node.asInt();
    }

    @JsonSetter("__fields")
    public void setFieldsFromJson(JsonNode node) {

    }

    public BasePost() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
    }

    protected BasePost(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
    }

    public static final Creator<BasePost> CREATOR = new Creator<BasePost>() {
        @Override
        public BasePost createFromParcel(Parcel source) {
            return new BasePost(source);
        }

        @Override
        public BasePost[] newArray(int size) {
            return new BasePost[size];
        }
    };
}
