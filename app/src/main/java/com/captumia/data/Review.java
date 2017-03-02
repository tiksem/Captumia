package com.captumia.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;
import com.utils.framework.Lists;
import com.utils.framework.strings.Strings;

import java.util.ArrayList;
import java.util.List;

public class Review implements Parcelable {
    public static final int RATING_CATEGORIES_COUNT = 4;
    public static final int MAX_RATING_VALUE = 5;

    private int id;
    @JsonProperty("author_name")
    private String authorName;
    @JsonProperty("author")
    private int authorId;
    private List<Integer> rating;
    @JsonProperty("date_ago")
    private String date;

    private String message;

    @JsonSetter("content")
    public void setDescriptionFromJson(JsonNode node) {
        JsonNode rendered = node.get("rendered");
        if (rendered != null && rendered.isTextual()) {
            message = rendered.asText();
            message = message.replace("<p>", "");
            message = message.replace("</p>", "");
            message = Strings.removeFromEnd(message, "\n");
        }
    }

    @JsonSetter("__fields")
    public void setFieldsFromJson(JsonNode node) {
        rating = new ArrayList<>(RATING_CATEGORIES_COUNT);
        for (int i = 0; i < RATING_CATEGORIES_COUNT; i++) {
            int ratingValue = Utils.getIntField(node, "star-rating-" + i, MAX_RATING_VALUE);
            rating.add(ratingValue);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public void setRating(List<Integer> rating) {
        this.rating = rating;
    }

    public List<Integer> getRating() {
        if (rating == null) {
            return Lists.repeatedValues(MAX_RATING_VALUE, RATING_CATEGORIES_COUNT);
        }

        return rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Review() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.authorName);
        dest.writeInt(this.authorId);
        dest.writeList(this.rating);
        dest.writeString(this.date);
        dest.writeString(this.message);
    }

    protected Review(Parcel in) {
        this.id = in.readInt();
        this.authorName = in.readString();
        this.authorId = in.readInt();
        this.rating = new ArrayList<Integer>();
        in.readList(this.rating, Integer.class.getClassLoader());
        this.date = in.readString();
        this.message = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel source) {
            return new Review(source);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
}
