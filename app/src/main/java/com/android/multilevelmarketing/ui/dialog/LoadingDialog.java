package com.android.multilevelmarketing.ui.dialog;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.multilevelmarketing.R;

public class LoadingDialog {
    public static ProgressDialog create(Context context) {
        ProgressDialog dialog = new ProgressDialog(context);

        dialog.setMessage(context.getResources().getString(R.string.dialog_loading));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }
}
