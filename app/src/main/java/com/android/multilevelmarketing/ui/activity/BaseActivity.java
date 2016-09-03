package com.android.multilevelmarketing.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.multilevelmarketing.data.api.TokenTracer;
import com.android.multilevelmarketing.data.sharedprefs.SharedPrefUtils;

public class BaseActivity extends BaseAnimActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TokenTracer.bind(this);

        if (!isSharedPrefsValid()) {
            finish();
            startActivity(new Intent(BaseActivity.this, FirstLaunchActivity.class));
        }
    }

    private boolean isSharedPrefsValid() {
        return !(SharedPrefUtils.getInstance(this).getApiKey().equals("") ||
                SharedPrefUtils.getInstance(this).getClientSecret().equals("") ||
                SharedPrefUtils.getInstance(this).getUUID().equals(""));
    }
}
