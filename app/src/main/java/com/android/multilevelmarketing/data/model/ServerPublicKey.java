package com.android.multilevelmarketing.data.model;

import com.google.gson.annotations.SerializedName;

public class ServerPublicKey {
    @SerializedName("public_key")
    private String key;

    public ServerPublicKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
