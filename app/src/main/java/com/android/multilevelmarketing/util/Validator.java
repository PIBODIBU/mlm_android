package com.android.multilevelmarketing.util;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.util.Log;

import java.util.LinkedList;

public class Validator {
    private final String TAG = getClass().getSimpleName();

    private LinkedList<TextInputLayout> layouts;

    public Validator() {
        layouts = new LinkedList<>();
    }

    public Validator addItem(@NonNull TextInputLayout textInputLayout) {
        if (layouts != null)
            this.layouts.add(textInputLayout);
        else
            Log.e(TAG, "addItem()-> data set is null");

        return this;
    }
}
