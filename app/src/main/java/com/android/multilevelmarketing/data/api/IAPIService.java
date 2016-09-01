package com.android.multilevelmarketing.data.api;

import com.android.multilevelmarketing.data.model.RegisterResponse;
import com.android.multilevelmarketing.data.model.ServerPublicKey;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface IAPIService {
    @FormUrlEncoded
    @POST("login")
    Call<RegisterResponse> login(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register")
    Call<RegisterResponse> register(
            @Field("name") String name,
            @Field("surname") String surname,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("username") String username,
            @Field("password") String password,
            @Field("refer") String refer
    );

    @Multipart
    @POST("test/upload")
    Call<ResponseBody> uploadPhoto(@Part("description") RequestBody description,
                                   @Part MultipartBody.Part file);
}