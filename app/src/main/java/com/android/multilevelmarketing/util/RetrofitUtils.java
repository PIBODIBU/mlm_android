package com.android.multilevelmarketing.util;

import retrofit2.Response;

public class RetrofitUtils {
    public static boolean isResponseValid(Response<?> response) {
        return !(response == null || response.body() == null || !response.isSuccessful());
    }
}
