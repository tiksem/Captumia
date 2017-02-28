package com.captumia.network;

import android.content.Context;

import com.utilsframework.android.network.retrofit.AuthenticationInterceptor;
import com.utilsframework.android.sp.SharedPreferencesMap;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;

public class LoginHandler {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    private final SharedPreferencesMap preferencesMap;

    public LoginHandler(Context context) {
        preferencesMap = new SharedPreferencesMap(context);
    }

    public void login(String password, String username) {
        preferencesMap.putString(PASSWORD, password);
        preferencesMap.putString(USERNAME, username);
    }

    public void logout() {
        preferencesMap.remove(PASSWORD);
        preferencesMap.remove(USERNAME);
    }

    public boolean isLoggedIn() {
        return preferencesMap.hasKey(USERNAME) && preferencesMap.hasKey(PASSWORD);
    }

    public String getLogin() {
        return preferencesMap.getString(USERNAME);
    }

    public String getPassword() {
        return preferencesMap.getString(PASSWORD);
    }
}
