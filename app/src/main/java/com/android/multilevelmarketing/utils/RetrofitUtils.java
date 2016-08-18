package com.android.multilevelmarketing.utils;

import retrofit2.Response;

public class RetrofitUtils {
    public static boolean isResponseValid(Response<?> response) {
        return !(response == null || response.body() == null);

    }
}
