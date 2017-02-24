package com.captumia.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenRequestData {
    private String token;
    @JsonProperty("user_display_name")
    private String userDisplayName;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }

    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }
}
