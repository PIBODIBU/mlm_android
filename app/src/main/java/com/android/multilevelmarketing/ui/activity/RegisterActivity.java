package com.android.multilevelmarketing.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.multilevelmarketing.R;

import butterknife.ButterKnife;

public class RegisterActivity extends BaseAnimActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }
}
