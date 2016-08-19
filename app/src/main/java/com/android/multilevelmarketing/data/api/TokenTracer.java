package com.android.multilevelmarketing.data.api;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.multilevelmarketing.data.sharedprefs.SharedPrefKeys;
import com.android.multilevelmarketing.data.sharedprefs.SharedPrefUtils;

public class TokenTracer {
    private final String TAG = getClass().getSimpleName();

    private static TokenTracer tokenTracer = null;
    private static SharedPreferences sharedPreferences = null;
    private static SharedPrefUtils sharedPrefUtils = null;

    private static SharedPreferences.OnSharedPreferenceChangeListener listener;

    public static TokenTracer bind(Context context) {
        if (tokenTracer == null) {
            tokenTracer = new TokenTracer(context);
        }

        return tokenTracer;
    }

    private TokenTracer(Context context) {
        sharedPreferences = context
                .getSharedPreferences(SharedPrefUtils.SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        sharedPrefUtils = SharedPrefUtils.getInstance(context);
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                switch (key) {
                    case SharedPrefKeys.API_KEY: {
                        sharedPrefUtils.clear();
                    }
                    case SharedPrefKeys.CLIENT_SECRET: {
                        sharedPrefUtils.clear();
                    }
                    case SharedPrefKeys.UUID: {
                        sharedPrefUtils.clear();
                    }
                    default:
                        break;
                }
            }
        };
    }

    public static void init() {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public void destroy() {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }
}
