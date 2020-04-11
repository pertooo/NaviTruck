package com.example.navitruck.network;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.navitruck.R;
import com.example.navitruck.Utils.Constants;
import com.example.navitruck.dto.UserDto;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthenticateRestClient {

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(Constants.API_BASE_URL)
                    .addConverterFactory(
                            GsonConverterFactory.create()
                    );

    Retrofit retrofit = builder.client(httpClient.build()).build();
    AuthenticateClient client = retrofit.create(AuthenticateClient.class);

    public void authenticate(UserDto userDto, Callback<ResponseBody> cb){

        Call<ResponseBody> call = client.authenticate(userDto);

        call.enqueue(cb);
    }


}
