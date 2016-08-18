package com.android.multilevelmarketing.data.firebase;

import com.google.firebase.iid.FirebaseInstanceIdService;

public class FBInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {

    }

    private void sendRegistrationToServer(String token) {

    }
}