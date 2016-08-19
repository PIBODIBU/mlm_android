package com.android.multilevelmarketing.data.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefUtils {
    private static SharedPrefUtils sharedPrefUtils = null;
    private SharedPreferences sharedPreferences;
    public static final String SHARED_PREFS_NAME = "com.android.multilevelmarketing";

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

    public SharedPrefUtils setName(String name) {
        sharedPreferences.edit().putString(SharedPrefKeys.NAME, name).apply();
        return this;
    }

    public SharedPrefUtils setSurname(String surname) {
        sharedPreferences.edit().putString(SharedPrefKeys.SURNAME, surname).apply();
        return this;
    }

    public SharedPrefUtils setEmail(String email) {
        sharedPreferences.edit().putString(SharedPrefKeys.EMAIL, email).apply();
        return this;
    }

    /**
     * GETTERS
     */
    public String getUUID() {
        return sharedPreferences.getString(SharedPrefKeys.UUID, "");
    }

    public String getApiKey() {

        return sharedPreferences.getString(SharedPrefKeys.API_KEY, "");
    }

    public String getClientSecret() {
        return sharedPreferences.getString(SharedPrefKeys.CLIENT_SECRET, "");
    }

    public String getName() {
        return sharedPreferences.getString(SharedPrefKeys.NAME, "");
    }

    public String getSurname() {
        return sharedPreferences.getString(SharedPrefKeys.SURNAME, "");
    }

    public String getEmail() {
        return sharedPreferences.getString(SharedPrefKeys.EMAIL, "");
    }

    public String getFullName() {
        return sharedPreferences.getString(SharedPrefKeys.NAME, "")
                + " "
                + sharedPreferences.getString(SharedPrefKeys.SURNAME, "");
    }
}
