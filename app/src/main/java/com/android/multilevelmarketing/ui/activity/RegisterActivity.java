package com.android.multilevelmarketing.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.widget.RelativeLayout;

import com.android.multilevelmarketing.R;
import com.android.multilevelmarketing.data.api.RetrofitAPI;
import com.android.multilevelmarketing.data.model.RegisterResponse;
import com.android.multilevelmarketing.data.sharedprefs.SharedPrefUtils;
import com.android.multilevelmarketing.ui.dialog.LoadingDialog;
import com.android.multilevelmarketing.util.RetrofitUtils;

import butterknife.BindInt;
import butterknife.BindString;
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

    @BindView(R.id.til_refer)
    public TextInputLayout TILMainRefer;

    @BindView(R.id.til_main_name)
    public TextInputLayout TILMainName;

    @BindView(R.id.til_main_surname)
    public TextInputLayout TILMainSurname;

    @BindView(R.id.til_main_email)
    public TextInputLayout TILMainEmail;

    @BindView(R.id.til_main_phone)
    public TextInputLayout TILMainPhone;

    @BindView(R.id.til_username)
    public TextInputLayout TILMainUsername;

    @BindView(R.id.til_password)
    public TextInputLayout TILMainPassword;

    @BindView(R.id.til_password_repeat)
    public TextInputLayout TILMainPasswordRepeat;

    /**
     * ERROR STRINGS
     **/
    @BindString(R.string.register_error_passwords_not_matching)
    public String ERROR_PASSWORDS_DO_NOT_MATCH;

    @BindString(R.string.register_error_empty_field)
    public String ERROR_EMPTY_FIELD;

    @BindString(R.string.register_error_contains_spaces)
    public String ERROR_FIELD_CONTAINS_SPACES;
    /**
     * INTEGERS
     **/
    @BindInt(R.integer.phone_max_length)
    public int MAX_LENGTH_PHONE;

    @BindInt(R.integer.username_max_length)
    public int MAX_LENGTH_USERNAME;

    @BindInt(R.integer.password_max_length)
    public int MAX_LENGTH_PASSWORD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_register)
    public void register() {
        final ProgressDialog loadingDialog = LoadingDialog.create(this);

        String refer = TILMainRefer.getEditText().getText().toString();
        String name = TILMainName.getEditText().getText().toString();
        String surname = TILMainSurname.getEditText().getText().toString();
        String email = TILMainEmail.getEditText().getText().toString();
        String phone = TILMainPhone.getEditText().getText().toString();
        String username = TILMainUsername.getEditText().getText().toString();
        String password = TILMainPassword.getEditText().getText().toString();
        String passwordRepeat = TILMainPasswordRepeat.getEditText().getText().toString();

        if (!checkForEmpty(TILMainRefer, TILMainName, TILMainSurname,
                TILMainEmail, TILMainPhone, TILMainUsername, TILMainPassword, TILMainPasswordRepeat)) {
            return;
        }

        if (!checkForSpaces(TILMainRefer, TILMainName, TILMainSurname,
                TILMainEmail, TILMainPhone, TILMainUsername, TILMainPassword, TILMainPasswordRepeat)) {
            return;
        }

        if (!validateForMatching(password, passwordRepeat)) {
            return;
        }

        loadingDialog.show();

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

    private boolean checkForEmpty(TextInputLayout... layouts) {
        boolean validationPassed = true;

        for (TextInputLayout textInputLayout : layouts) {
            if (textInputLayout.getEditText().getText().toString().trim().equals("")) {
                validationPassed = false;
                textInputLayout.setError(ERROR_EMPTY_FIELD);
            } else {
                removeErrorFromTIL(textInputLayout);
            }
        }

        return validationPassed;
    }

    private boolean checkForSpaces(TextInputLayout... layouts) {
        boolean validationPassed = true;

        for (TextInputLayout textInputLayout : layouts) {
            if (textInputLayout.getEditText().getText().toString().trim().contains(" ")) {
                validationPassed = false;
                textInputLayout.setError(ERROR_FIELD_CONTAINS_SPACES);
            } else {
                textInputLayout.setError("");
            }
        }

        return validationPassed;
    }

    private boolean validateForMatching(String str1, String str2) {
        boolean matched = str1.trim().equals(str2.trim());

        if (!matched) {
            TILMainPasswordRepeat.setError(ERROR_PASSWORDS_DO_NOT_MATCH);
        } else {
            removeErrorFromTIL(TILMainPasswordRepeat);
        }

        return matched;
    }

    private void removeErrorFromTIL(TextInputLayout... layouts) {
        for (TextInputLayout textInputLayout : layouts) {
            textInputLayout.setError("");
        }
    }
}
