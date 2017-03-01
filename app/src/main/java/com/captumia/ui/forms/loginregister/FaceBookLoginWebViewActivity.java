package com.captumia.ui.forms.loginregister;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebView;

import com.captumia.app.CaptumiaApplication;
import com.captumia.app.NetworkHandler;
import com.captumia.events.FacebookLoginFinished;
import com.captumia.network.RestApiClient;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.utils.framework.CollectionUtils;
import com.utils.framework.Transformer;
import com.utils.framework.strings.Strings;
import com.utilsframework.android.view.Toasts;
import com.utilsframework.android.web.WebViewActivity;

import org.greenrobot.eventbus.EventBus;

import java.net.HttpCookie;
import java.util.Collections;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

public class FaceBookLoginWebViewActivity extends WebViewActivity {
    public static final String FACEBOOK_LOGIN_URL = "https://www.captumia.com/?wc-api=auth&start=" +
            "facebook&return=https%3A%2F%2Fwww.captumia.com%2Fmyaccount%2F";
    public static final int LOGIN_COOKIE_EXPIRATION_TIME = 10 * 24 * 3600 * 1000;

    @Override
    protected void onCreateLoadData(Intent intent) {
        getWebView().setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                FaceBookLoginWebViewActivity.this.onPageFinished(url);
            }
        });
        getWebView().loadUrl(FACEBOOK_LOGIN_URL);
    }

    private void onPageFinished(String url) {
        if (url.contains(RestApiClient.DOMAIN) && !url.contains("facebook")) {
            String cookieString = CookieManager.getInstance().getCookie(url);
            String loginCookie = Strings.findStringStartedWith(
                    NetworkHandler.LOGGED_IN_COOKIE_TOKEN, cookieString);
            Cookie cookie = null;
            if (loginCookie != null) {
                Cookie parsedCookie = Cookie.parse(HttpUrl.parse(url), loginCookie);
                SharedPrefsCookiePersistor cookiesStore = CaptumiaApplication.
                        getInstance().getNetworkHandler().getPersistedCookies();
                cookie = new Cookie.Builder()
                        .name(parsedCookie.name())
                        .domain(parsedCookie.domain())
                        .expiresAt(System.currentTimeMillis() +
                                LOGIN_COOKIE_EXPIRATION_TIME)
                        .path("/")
                        .value(parsedCookie.value()).build();
                cookiesStore.saveAll(Collections.singleton(cookie));
            }

            finish();
            EventBus.getDefault().post(new FacebookLoginFinished(cookie));
        }
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, FaceBookLoginWebViewActivity.class);
        context.startActivity(intent);
    }
}
