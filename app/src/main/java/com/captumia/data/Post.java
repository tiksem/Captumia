package com.captumia.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;

public class Post {
    private int id;
    @JsonProperty("better_featured_image")
    private Media media;
    @JsonIgnore
    private String title;

    @JsonSetter("title")
    public void setTitleFromJson(JsonNode jsonNode) {
        title = jsonNode.get("rendered").asText();
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
}
