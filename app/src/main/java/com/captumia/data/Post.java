package com.captumia.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;
import com.utils.framework.strings.Strings;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class Post implements Parcelable {
    private static final Pattern GALLERY_IMAGE_PATTERN = Pattern.compile("\"([^\"]+)\"");

    private int id;
    @JsonProperty("better_featured_image")
    private Media media;
    @JsonIgnore
    private String title;
    @JsonIgnore
    private String phone;
    @JsonIgnore
    private List<String> address;
    @JsonIgnore
    private List<String> photos;
    @JsonIgnore
    private String description;

    @JsonSetter("title")
    public void setTitleFromJson(JsonNode jsonNode) {
        title = jsonNode.get("rendered").asText();
    }

    private String getStringField(JsonNode fields, String key) {
        JsonNode array = fields.get(key);
        if (array != null && array.size() > 0) {
            String value = array.get(0).asText();
            if (Strings.isEmpty(value)) {
                return null;
            }

            return value;
        }

        return null;
    }

    @JsonSetter("__fields")
    public void setFieldsFromJson(JsonNode node) {
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
        photos = Strings.findAll(galleryImagesString, GALLERY_IMAGE_PATTERN);
    }

    @JsonSetter("content")
    public void setDescriptionFromJson(JsonNode node) {
        JsonNode rendered = node.get("rendered");
        if (rendered != null && rendered.isTextual()) {
            description = rendered.asText();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public String getTitle() {
        return title;
    }

    public String getPhone() {
        return phone;
    }

    public List<String> getAddress() {
        return address;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public String getDescription() {
        return description;
    }

    public Post() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeParcelable(this.media, flags);
        dest.writeString(this.title);
        dest.writeString(this.phone);
        dest.writeStringList(this.address);
        dest.writeStringList(this.photos);
        dest.writeString(this.description);
    }

    protected Post(Parcel in) {
        this.id = in.readInt();
        this.media = in.readParcelable(Media.class.getClassLoader());
        this.title = in.readString();
        this.phone = in.readString();
        this.address = in.createStringArrayList();
        this.photos = in.createStringArrayList();
        this.description = in.readString();
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
