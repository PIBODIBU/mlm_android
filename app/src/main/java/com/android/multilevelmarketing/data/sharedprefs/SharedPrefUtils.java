package com.android.multilevelmarketing.data.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefUtils {
    private static SharedPrefUtils sharedPrefUtils = null;
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS_NAME = "com.android.multilevelmarketing";

    public static SharedPrefUtils getInstance(Context context) {
        if (sharedPrefUtils == null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
            return new SharedPrefUtils(sharedPreferences);
        } else {
            return sharedPrefUtils;
        }
    }

    public SharedPrefUtils(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void clear() {
        sharedPreferences.edit().clear().apply();
    }

    /**
     * SETTERS
     */
    public SharedPrefUtils setUUID(String UUID) {
        sharedPreferences.edit().putString(SharedPrefKeys.UUID, UUID).apply();
        return this;
    }

    public SharedPrefUtils setApiKey(String apiKey) {
        sharedPreferences.edit().putString(SharedPrefKeys.API_KEY, apiKey).apply();
        return this;
    }

    public SharedPrefUtils setClientSecret(String clientSecret) {
        sharedPreferences.edit().putString(SharedPrefKeys.CLIENT_SECRET, clientSecret).apply();
        return this;
    }

    /**
     * GETTERS
     */
    public void getUUID() {
        sharedPreferences.getString(SharedPrefKeys.UUID, "");
    }

    public void getApiKey() {
        sharedPreferences.getString(SharedPrefKeys.API_KEY, "");
    }

    public void getClientSecret() {
        sharedPreferences.getString(SharedPrefKeys.CLIENT_SECRET, "");
    }

}
