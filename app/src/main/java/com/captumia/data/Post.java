package com.captumia.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;
import com.utils.framework.Lists;
import com.utils.framework.strings.Strings;
import com.utilsframework.android.network.retrofit.RequestBodies;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Post extends BasePost implements Parcelable {
    public static final int TOP_REVIEWS_MAX_COUNT = 3;
    private static final Pattern GALLERY_IMAGE_PATTERN = Pattern.compile("\"([^\"]+)\"");

    @JsonProperty("better_featured_image")
    private Media media;
    @JsonIgnore
    private String phone;
    @JsonIgnore
    private List<String> address;
    @JsonIgnore
    private List<String> photos;
    @JsonIgnore
    private String description;
    private List<Tag> tags;
    @JsonIgnore
    private String email;
    @JsonIgnore
    private String website;
    @JsonIgnore
    private String videoUrl;
    @JsonIgnore
    private String location;
    @JsonIgnore
    private Region region;
    @JsonIgnore
    private List<OperatingHoursItem> operationHours;
    @JsonIgnore
    private Category category;
    @JsonProperty("top_reviews")
    private List<Review> topReviews;
    @JsonIgnore
    private int rating;

    @JsonSetter("__fields")
    public void setFieldsFromJson(JsonNode node) {
        super.setFieldsFromJson(node);
        phone = getStringField(node, "_phone");

        String streetNumber = getStringField(node, "geolocation_street_number");
        String street = getStringField(node, "geolocation_street");
        CharSequence addressLine1 = Strings.joinObjects(" ", streetNumber, street);
        CharSequence addressLine2;
        String city = getStringField(node, "geolocation_city");
        String state = getStringField(node, "geolocation_state_long");
        if (addressLine1.length() == 0) {
            addressLine1 = city;
            addressLine2 = state;
        } else {
            String postCode = getStringField(node, "geolocation_postcode");
            addressLine2 = Strings.joinObjects(" ", city, state, postCode);
        }

        address = Strings.stringListExcludeEmpty(addressLine1, addressLine2);

        String galleryImagesString = getStringField(node, "_gallery_images");
        if (galleryImagesString != null) {
            photos = Strings.findAll(galleryImagesString, GALLERY_IMAGE_PATTERN);
        }

        rating = getIntField(node, "rating", 0);
    }

    @JsonSetter("content")
    public void setDescriptionFromJson(JsonNode node) {
        JsonNode rendered = node.get("rendered");
        if (rendered != null && rendered.isTextual()) {
            description = rendered.asText();
        }
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public String getPhone() {
        return phone;
    }

    public List<String> getAddress() {
        if (address == null) {
            String regionName = region == null ? null : region.getName();
            return Lists.withoutNulls(location, regionName);
        }

        return address;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public String getDescription() {
        return description;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public List<OperatingHoursItem> getOperationHours() {
        return operationHours;
    }

    public void setOperationHours(List<OperatingHoursItem> operationHours) {
        this.operationHours = operationHours;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCoverImage() {
        Media media = getMedia();
        if (media == null) {
            return null;
        }

        return media.getSourceUrl();
    }

    public List<Review> getTopReviews() {
        return topReviews;
    }

    public void setTopReviews(List<Review> topReviews) {
        this.topReviews = topReviews;
    }

    public Post() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.media, flags);
        dest.writeString(this.phone);
        dest.writeStringList(this.address);
        dest.writeStringList(this.photos);
        dest.writeString(this.description);
        dest.writeTypedList(this.tags);
        dest.writeString(this.email);
        dest.writeString(this.website);
        dest.writeString(this.videoUrl);
        dest.writeString(this.location);
        dest.writeParcelable(this.region, flags);
        dest.writeTypedList(this.operationHours);
        dest.writeParcelable(this.category, flags);
        dest.writeTypedList(this.topReviews);
        dest.writeInt(this.rating);
    }

    protected Post(Parcel in) {
        super(in);
        this.media = in.readParcelable(Media.class.getClassLoader());
        this.phone = in.readString();
        this.address = in.createStringArrayList();
        this.photos = in.createStringArrayList();
        this.description = in.readString();
        this.tags = in.createTypedArrayList(Tag.CREATOR);
        this.email = in.readString();
        this.website = in.readString();
        this.videoUrl = in.readString();
        this.location = in.readString();
        this.region = in.readParcelable(Region.class.getClassLoader());
        this.operationHours = in.createTypedArrayList(OperatingHoursItem.CREATOR);
        this.category = in.readParcelable(Category.class.getClassLoader());
        this.topReviews = in.createTypedArrayList(Review.CREATOR);
        this.rating = in.readInt();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel source) {
            return new Post(source);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
}
