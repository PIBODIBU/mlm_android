package com.android.multilevelmarketing.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.multilevelmarketing.R;
import com.android.multilevelmarketing.ui.activity.RegisterActivity;
import com.android.multilevelmarketing.util.CircleTransform;
import com.android.multilevelmarketing.util.Permissions;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentRegisterAvatar extends Fragment {
    private final String TAG = getClass().getSimpleName();

    @BindView(R.id.root_view_info_avatar)
    public RelativeLayout rootView;

    @BindView(R.id.iv_photo)
    public ImageView IVPhoto;

    @BindView(R.id.rl_iv_photo_wrapper)
    public RelativeLayout RLPhotoWrapper;

    @BindView(R.id.tv_text_title)
    public TextView TVTitle;

    @BindView(R.id.tv_text_text)
    public TextView TVText;

    private static final int REQUEST_CODE_PICK_IMAGE = 10;

    private Uri selectedImageUri = null;

    public final String TITLE_NOT_UPLOADED = "Upload your photo";
    public final String TEXT_NOT_UPLOADED = "This is the final step. Upload your photo and your friends will recognize you.";
    public final String TITLE_UPLOADED = "You are awesome!";
    public final String TEXT_UPLOADED = "Click the button below to register.";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fm_register_avatar, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_PICK_IMAGE:
                if (data == null || data.getData() == null) {
                    if (selectedImageUri != null) {
                        return;
                    }

                    RLPhotoWrapper.setBackground(null);
                    IVPhoto.setImageResource(R.drawable.ic_add_a_photo_white_24dp);
                    TVTitle.setText(TITLE_NOT_UPLOADED);
                    TVText.setText(TEXT_NOT_UPLOADED);
                    return;
                }

                selectedImageUri = data.getData();
                TVTitle.setText(TITLE_UPLOADED);
                TVText.setText(TEXT_UPLOADED);
                Picasso
                        .with(getActivity())
                        .load(selectedImageUri)
                        .transform(new CircleTransform())
                        .into(IVPhoto, new Callback() {
                            @Override
                            public void onSuccess() {
                                RLPhotoWrapper.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.circle_border_white));
                            }

                            @Override
                            public void onError() {
                            }
                        });
        }
    }

    @OnClick({R.id.iv_photo, R.id.btn_gallery})
    public void pickPhotoFromGallery() {
        Permissions.verifyStoragePermissions(getActivity());

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(i, REQUEST_CODE_PICK_IMAGE);
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
        }
    }

}
