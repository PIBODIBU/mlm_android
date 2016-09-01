package com.android.multilevelmarketing.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.multilevelmarketing.R;
import com.android.multilevelmarketing.data.api.RetrofitAPI;
import com.android.multilevelmarketing.data.keys.BundleKeys;
import com.android.multilevelmarketing.data.model.RegisterResponse;
import com.android.multilevelmarketing.data.sharedprefs.SharedPrefUtils;
import com.android.multilevelmarketing.ui.dialog.LoadingDialog;
import com.android.multilevelmarketing.util.RetrofitUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseAnimActivity {
    public final String TAG = getClass().getSimpleName();

    @BindView(R.id.root_view)
    public RelativeLayout rootView;

    @BindView(R.id.iv_logo)
    public ImageView IVLogo;

    @BindView(R.id.et_username)
    public EditText ETUsername;

    @BindView(R.id.et_password)
    public EditText ETPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setupLayout();

        if (savedInstanceState != null) {
            ETUsername.setText(savedInstanceState.getString(BundleKeys.USERNAME, ""));
            ETPassword.setText(savedInstanceState.getString(BundleKeys.PASSWORD, ""));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(BundleKeys.USERNAME, ETUsername.getText().toString());
        outState.putString(BundleKeys.PASSWORD, ETPassword.getText().toString());

        super.onSaveInstanceState(outState);
    }

    private void setupLayout() {
        Picasso
                .with(this)
                .load(R.drawable.logo_green_yellow)
                .into(IVLogo);
    }

    private boolean isInputValid() {
        String username = ETUsername.getText().toString();
        String password = ETPassword.getText().toString();

        if (username.equals("") || password.equals(""))
            return false;
        return true;
    }

    @OnClick(R.id.btn_login)
    public void login() {
        final ProgressDialog loadingDialog = LoadingDialog.create(this);
        String username = ETUsername.getText().toString();
        String password = ETPassword.getText().toString();

        if (!isInputValid()) {
            return;
        }

        loadingDialog.show();

        RetrofitAPI.getInstance().login(
                username,
                password
        ).enqueue(new Callback<RegisterResponse>() {
            private void onError() {
                Snackbar.make(rootView, getResources().getString(R.string.toast_login_failed), Snackbar.LENGTH_LONG).show();
                loadingDialog.cancel();
            }

            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (!RetrofitUtils.isResponseValid(response)) {
                    Log.e(TAG, "onResponse()-> error");
                    onError();
                    return;
                }

                final RegisterResponse registerResponse = response.body();

                if (response.body().isError()) {
                    loadingDialog.cancel();
                    Snackbar.make(rootView, getResources().getString(R.string.toast_login_bad_creds), Snackbar.LENGTH_LONG).show();
                    return;
                }

                SharedPrefUtils.getInstance(LoginActivity.this)
                        .setUUID(registerResponse.getMainInfo().getUuid())
                        .setApiKey(registerResponse.getMainInfo().getApiKey())
                        .setClientSecret(registerResponse.getMainInfo().getClientSecret())
                        .setName(registerResponse.getMainInfo().getName())
                        .setSurname(registerResponse.getMainInfo().getSurname())
                        .setEmail(registerResponse.getMainInfo().getEmail());

                loadingDialog.cancel();
                finish();
                startActivity(new Intent(LoginActivity.this, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.e(TAG, "onFailure()-> ", t);
                onError();
            }
        });
    }
}
