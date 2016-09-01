package com.android.multilevelmarketing.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.multilevelmarketing.R;
import com.android.multilevelmarketing.data.api.RetrofitAPI;
import com.android.multilevelmarketing.util.CircleTransform;
import com.android.multilevelmarketing.util.FileUtils;
import com.android.multilevelmarketing.util.Permissions;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.data;

public class MainActivity extends BaseNavDrawerActivity {
    private final String TAG = getClass().getSimpleName();
    private static final int REQUEST_CODE_PICK_IMAGE = 10;

    private Uri selectedImageUri = null;

    @BindView(R.id.iv_photo)
    public ImageView IVPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getDrawer();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_PICK_IMAGE:
                selectedImageUri = data.getData();

                Picasso
                        .with(this)
                        .load(selectedImageUri)
                        .transform(new CircleTransform())
                        .into(IVPhoto);
        }
    }

    @OnClick(R.id.iv_photo)
    public void pickPhoto() {
        Permissions.verifyStoragePermissions(this);

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

    @OnClick(R.id.btn_upload)
    public void uploadFile() {
        if (selectedImageUri == null) {
            Log.e(TAG, "uploadFile()-> File uri is null");
            return;
        }

        File file = new File(FileUtils.getRealPathFromURI(this, selectedImageUri));

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("userfile", file.getName(), requestFile);

        // add another part within the multipart request
        String descriptionString = "hello, this is description speaking";
        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), descriptionString);

        // finally, execute the request
        RetrofitAPI.getInstance().uploadPhoto(description, body)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call,
                                           Response<ResponseBody> response) {
                        Log.d("Upload", "success");
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("Upload error:", t.getMessage());
                    }
                });
    }
}
