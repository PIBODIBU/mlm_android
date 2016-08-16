package com.android.multilevelmarketing.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.android.multilevelmarketing.R;
import com.android.multilevelmarketing.data.api.RetrofitAPI;
import com.android.multilevelmarketing.data.model.RegisterResponse;
import com.android.multilevelmarketing.data.model.ServerPublicKey;

import org.apache.commons.codec.binary.Hex;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.ECField;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    @BindView(R.id.et_username)
    public EditText ETUsername;

    @BindView(R.id.et_password)
    public EditText ETPassword;

    @BindView(R.id.text_view)
    public TextView textView;

    private KeyPair keyPair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.fab)
    public void go() {

        String username = ETUsername.getText().toString();
        String password = ETPassword.getText().toString();

        RetrofitAPI.getInstance().login(
                username,
                password
        ).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response == null || response.body() == null) {
                    Log.e(TAG, "onResponse()-> error");
                    return;
                }

                final RegisterResponse registerResponse = response.body();

                textView.setText("" +
                        "UUID: " + registerResponse.getUuid() +
                        "\n\n" +
                        "Api key: " + registerResponse.getApiKey() +
                        "\n\n" +
                        "Secret: " + registerResponse.getClientSecret()
                );
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.e(TAG, "onFailure()-> ", t);
            }
        });
    }
}
