package com.captumia.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;

public class Media implements Parcelable {
    @JsonIgnore
    private String displayInListUrl;
    @JsonProperty("source_url")
    private String sourceUrl;

    @JsonSetter("media_details")
    public void setImageDetails(JsonNode jsonNode) {
        JsonNode largeNode = jsonNode.get("sizes").get("large");
        if (largeNode != null) {
            displayInListUrl = largeNode.get("source_url").asText();
        }
    }

    public String getDisplayInListUrl() {
        if (displayInListUrl == null) {
            return sourceUrl;
        }

        return displayInListUrl;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.displayInListUrl);
        dest.writeString(this.sourceUrl);
    }

    public Media() {
    }

    protected Media(Parcel in) {
        this.displayInListUrl = in.readString();
        this.sourceUrl = in.readString();
    }

    public static final Parcelable.Creator<Media> CREATOR = new Parcelable.Creator<Media>() {
        @Override
        public Media createFromParcel(Parcel source) {
            return new Media(source);
        }

        @Override
        public Media[] newArray(int size) {
            return new Media[size];
        }
    };
}
