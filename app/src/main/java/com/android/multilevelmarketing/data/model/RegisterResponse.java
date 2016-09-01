package com.android.multilevelmarketing.data.model;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

    @SerializedName("main_info")
    private UserMainInfo mainInfo;

    @SerializedName("error")
    private boolean error;

    @SerializedName("error_message")
    private String errorMessage;

    @SerializedName("error_code")
    private int errorCode;

    public UserMainInfo getMainInfo() {
        return mainInfo;
    }

    public boolean isError() {
        return error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
