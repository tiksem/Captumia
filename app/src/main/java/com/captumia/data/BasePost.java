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
        return Utils.getFieldItem(fields, key);
    }

    protected final String getStringField(JsonNode fields, String key) {
        return Utils.getStringField(fields, key);
    }

    protected final int getIntField(JsonNode fields, String key, int defaultValue) {
        return Utils.getIntField(fields, key, defaultValue);
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

}
