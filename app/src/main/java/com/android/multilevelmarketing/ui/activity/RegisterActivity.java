package com.android.multilevelmarketing.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.android.multilevelmarketing.R;
import com.android.multilevelmarketing.data.api.RetrofitAPI;
import com.android.multilevelmarketing.data.model.RegisterResponse;
import com.android.multilevelmarketing.data.sharedprefs.SharedPrefUtils;
import com.android.multilevelmarketing.ui.dialog.LoadingDialog;
import com.android.multilevelmarketing.util.RetrofitUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseAnimActivity {
    private final String TAG = getClass().getSimpleName();

    @BindView(R.id.root_view)
    public RelativeLayout rootView;

    @BindView(R.id.et_refer)
    public EditText mainRefer;

    @BindView(R.id.et_main_name)
    public EditText mainName;

    @BindView(R.id.et_main_surname)
    public EditText mainSurname;

    @BindView(R.id.et_main_email)
    public EditText mainEmail;

    @BindView(R.id.et_main_phone)
    public EditText mainPhone;

    @BindView(R.id.et_username)
    public EditText mainUsername;

    @BindView(R.id.et_password)
    public EditText mainPassword;

    @BindView(R.id.et_password_repeat)
    public EditText mainPasswordRepeat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_register)
    public void register() {
        final ProgressDialog loadingDialog = LoadingDialog.create(this);
        loadingDialog.show();

        String refer = mainRefer.getText().toString();
        String name = mainName.getText().toString();
        String surname = mainSurname.getText().toString();
        String email = mainEmail.getText().toString();
        String phone = mainPhone.getText().toString();
        String username = mainUsername.getText().toString();
        String password = mainPassword.getText().toString();
        String passwordRepeat = mainPasswordRepeat.getText().toString();

        RetrofitAPI.getInstance().register(
                name,
                surname,
                email,
                phone,
                username,
                password,
                refer
        ).enqueue(new Callback<RegisterResponse>() {
            private void onError() {
                Snackbar.make(rootView, getResources().getString(R.string.toast_register_failed), Snackbar.LENGTH_LONG).show();
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

                Log.d(TAG, "onResponse()-> response.body().isError(): " + response.body().isError());

                if (response.body().isError()) {
                    Log.d(TAG, "onResponse()-> bad creds");
                    Snackbar.make(rootView, getResources().getString(R.string.toast_register_server_error), Snackbar.LENGTH_LONG).show();
                    return;
                }

                SharedPrefUtils.getInstance(RegisterActivity.this)
                        .setUUID(registerResponse.getUuid())
                        .setApiKey(registerResponse.getApiKey())
                        .setClientSecret(registerResponse.getClientSecret())
                        .setName(registerResponse.getName())
                        .setSurname(registerResponse.getSurname())
                        .setEmail(registerResponse.getEmail());

                loadingDialog.cancel();
                finish();
                startActivity(new Intent(RegisterActivity.this, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                onError();
            }
        });
    }
}
