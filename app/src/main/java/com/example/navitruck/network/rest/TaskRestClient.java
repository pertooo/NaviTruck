package com.example.navitruck.network.rest;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.navitruck.R;
import com.example.navitruck.Utils.Constants;
import com.example.navitruck.callback.TaskAcceptCallback;
import com.example.navitruck.dto.abst.AbstractDTO;
import com.example.navitruck.dto.response.ResponseTaskDTO;
import com.example.navitruck.network.Client;
import com.example.navitruck.network.TaskClient;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TaskRestClient {

    private Activity activity;

    public TaskRestClient(Activity activity){
        this.activity = activity;
    }

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .connectTimeout(300, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {

            SharedPreferences sharedPref = activity.getSharedPreferences(Constants.SETTINGS, Context.MODE_PRIVATE);   //activity.getPreferences(Context.MODE_PRIVATE);
            String token = sharedPref.getString(activity.getString(R.string.token),"");

            return chain.proceed(
                    chain.request()
                            .newBuilder()
                            .addHeader(Constants.HEADER_STRING, token)
                            .build());
        }
    });

    Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(Constants.API_BASE_URL)
                    .addConverterFactory(
                            GsonConverterFactory.create()
                    );

    Retrofit retrofit = builder.client(httpClient.build()).build();
    TaskClient client = retrofit.create(TaskClient.class);

    public void accept(long taskId, long userId, double fee, Callback<ResponseTaskDTO<Object>> cb){

        Call<ResponseTaskDTO<Object>> call = client.accept(taskId, userId, fee);
        call.enqueue(cb);
    }
}
