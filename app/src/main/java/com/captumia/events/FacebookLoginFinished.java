package com.captumia.events;

import java.util.List;

import okhttp3.Cookie;

public class FacebookLoginFinished {
    private Cookie cookie;

    public FacebookLoginFinished(Cookie cookie) {
        this.cookie = cookie;
    }

    public Cookie getCookie() {
        return cookie;
    }
}
