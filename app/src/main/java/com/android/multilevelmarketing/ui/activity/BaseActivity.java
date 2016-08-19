package com.android.multilevelmarketing.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.multilevelmarketing.R;
import com.android.multilevelmarketing.data.api.TokenTracer;
import com.android.multilevelmarketing.data.sharedprefs.SharedPrefUtils;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TokenTracer.bind(this);

        if (!isSharedPrefsValid()) {
            finish();
            startActivity(new Intent(BaseActivity.this, FirstLaunchActivity.class));
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);

        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);

        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }


    private boolean isSharedPrefsValid() {
        return !(SharedPrefUtils.getInstance(this).getApiKey().equals("") ||
                SharedPrefUtils.getInstance(this).getClientSecret().equals("") ||
                SharedPrefUtils.getInstance(this).getUUID().equals(""));
    }
}
