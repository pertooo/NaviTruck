package com.example.navitruck.network;

import com.example.navitruck.dto.AbstractDTO;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    String API_BASE_URL = "http://192.168.1.11:8080";

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(
                            GsonConverterFactory.create()
                    );

    Retrofit retrofit = builder.client(httpClient.build()).build();
    Client client = retrofit.create(Client.class);


    public void test(){

        Call<List<AbstractDTO>> call = client.test();

        call.enqueue(new Callback<List<AbstractDTO>>() {
            @Override
            public void onResponse(Call<List<AbstractDTO>> call, Response<List<AbstractDTO>> response) {
                // The network call was a success and we got a response
                // TODO: use the repository list and display it
                System.out.println(123);
            }

            @Override
            public void onFailure(Call<List<AbstractDTO>> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error

                System.out.println(321);
            }
        });
    }

    public void sendData(String note, String zip, String status){

        Call<List<AbstractDTO>> call = client.sendData(note, zip, status);

        call.enqueue(new Callback<List<AbstractDTO>>() {
            @Override
            public void onResponse(Call<List<AbstractDTO>> call, Response<List<AbstractDTO>> response) {
                // The network call was a success and we got a response
                // TODO: use the repository list and display it
                System.out.println(123);
            }

            @Override
            public void onFailure(Call<List<AbstractDTO>> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error

                System.out.println(321);
            }
        });
    }


}
