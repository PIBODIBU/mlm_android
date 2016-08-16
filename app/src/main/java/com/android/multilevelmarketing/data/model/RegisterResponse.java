package com.android.multilevelmarketing.data.model;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

    @SerializedName("api_key")
    private String apiKey;

    @SerializedName("client_secret")
    private String clientSecret;

    @SerializedName("uuid")
    private String uuid;

    public RegisterResponse(String apiKey, String clientSecret, String uuid) {
        this.apiKey = apiKey;
        this.clientSecret = clientSecret;
        this.uuid = uuid;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getUuid() {
        return uuid;
    }
}
