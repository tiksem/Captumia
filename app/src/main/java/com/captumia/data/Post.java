package com.captumia.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;
import com.utils.framework.strings.Strings;

public class Post {
    private int id;
    @JsonProperty("better_featured_image")
    private Media media;
    @JsonIgnore
    private String title;
    @JsonIgnore
    private String phone;

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
}
