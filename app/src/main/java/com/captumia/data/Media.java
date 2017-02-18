package com.captumia.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;

public class Media {
    @JsonIgnore
    private String displayInListUrl;
    @JsonProperty("source_url")
    private String sourceUrl;

    @JsonSetter("media_details")
    public void setImageDetails(JsonNode jsonNode) {
        displayInListUrl = jsonNode.get("sizes").get("medium").get("source_url").asText();
    }

    public String getDisplayInListUrl() {
        return displayInListUrl;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
}
