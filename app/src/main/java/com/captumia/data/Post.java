package com.captumia.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Post {
    private int id;
    @JsonProperty("better_featured_image")
    private Media media;

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
}
