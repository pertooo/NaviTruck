package com.example.navitruck.network;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.navitruck.R;
import com.example.navitruck.dto.AbstractDTO;
import com.example.navitruck.dto.UserDto;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthenticateRestClient {


    private String API_BASE_URL = "http://192.168.1.11:8080";
    private String headerString = "Authorization";

    private Activity activity;

    public AuthenticateRestClient(Activity activity){
        this.activity = activity;
    }

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
            String token = sharedPref.getString(activity.getString(R.string.token),"");

            return chain.proceed(
                    chain.request()
                            .newBuilder()
                            .addHeader(headerString, token) //TODO TOKEN
                            .build());
        }
    });

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
