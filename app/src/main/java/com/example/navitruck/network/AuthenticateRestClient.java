package com.example.navitruck.network;

import com.example.navitruck.dto.AbstractDTO;
import com.example.navitruck.dto.UserDto;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthenticateRestClient {

    String API_BASE_URL = "http://192.168.1.11:8080";

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(
                            GsonConverterFactory.create()
                    );

    Retrofit retrofit = builder.client(httpClient.build()).build();
    AuthenticateClient client = retrofit.create(AuthenticateClient.class);

    public void authenticate(UserDto userDto, Callback<ResponseBody> cb){

        userDto.setUsername("android");
        userDto.setPassword("android");

        Call<ResponseBody> call = client.login(userDto);

        call.enqueue(cb);
    }
}
