package com.android.multilevelmarketing.ui.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.multilevelmarketing.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirstLaunchActivity extends BaseAnimActivity {
    public final String TAG = getClass().getSimpleName();

    @BindView(R.id.tv_forgot_password)
    public TextView TVForgotPassword;

    @BindView(R.id.iv_logo)
    public ImageView IVLogo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_launch);
        ButterKnife.bind(this);

        setupLayout();
    }

    private void setupLayout() {
        TVForgotPassword.setPaintFlags(TVForgotPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Picasso
                .with(this)
                .load(R.drawable.logo_green_yellow)
                .into(IVLogo);
    }

    @OnClick(R.id.btn_login)
    public void startLoginActivity() {
        finish();
        startActivity(new Intent(FirstLaunchActivity.this, LoginActivity.class));
    }

    @OnClick(R.id.btn_register)
    public void startRegisterActivity() {
        finish();
        startActivity(new Intent(FirstLaunchActivity.this, RegisterActivity.class));
    }
}
