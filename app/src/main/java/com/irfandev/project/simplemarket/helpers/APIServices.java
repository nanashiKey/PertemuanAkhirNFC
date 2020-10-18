package com.irfandev.project.simplemarket.helpers;

import com.irfandev.project.simplemarket.models.BarangResponse;
import com.irfandev.project.simplemarket.models.DefaultResponse;
import com.irfandev.project.simplemarket.models.UsersResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * created by Irfan Assidiq
 * email : assidiq.irfan@gmail.com
 **/
public interface APIServices {
    @FormUrlEncoded
    @POST("masuk")
    public Call<UsersResponse> userLogin(
                        @Field("username") String username,
                        @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    public Call<DefaultResponse> userRegister(
                        @Field("username") String username,
                        @Field("email") String email,
                        @Field("password") String password,
                        @Field("point") int point);


    @GET("getallbarang")
    public Call<BarangResponse> getAllBarang();
}
