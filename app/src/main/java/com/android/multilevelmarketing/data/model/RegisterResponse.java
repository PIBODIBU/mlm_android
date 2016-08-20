package com.android.multilevelmarketing.data.model;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

    @SerializedName("api_key")
    private String apiKey;

    @SerializedName("client_secret")
    private String clientSecret;

    @SerializedName("uuid")
    private String uuid;

    @SerializedName("name")
    private String name;

    @SerializedName("surname")
    private String surname;

    @SerializedName("email")
    private String email;

    @SerializedName("error")
    private boolean error;

    @SerializedName("error_message")
    private String errorMessage;

    public RegisterResponse(
            String apiKey,
            String clientSecret,
            String uuid,
            String name,
            String surname,
            String email,
            boolean error,
            String errorMessage
    ) {
        this.apiKey = apiKey;
        this.clientSecret = clientSecret;
        this.uuid = uuid;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.error = error;
        this.errorMessage = errorMessage;
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

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public boolean isError() {
        return error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
