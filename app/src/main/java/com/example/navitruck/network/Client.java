package com.example.navitruck.network;

import com.example.navitruck.dto.abst.AbstractDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Client {
    @GET("/users/{user}/repos")
    Call<List<AbstractDTO>> reposForUser(
            @Path("user") String user
    );


    @GET("/email/get")
    Call<List<AbstractDTO>> test();

    @FormUrlEncoded
    @POST("/email/data")
    Call<List<AbstractDTO>> sendData(
            @Field("note") String note,
            @Field("zip") String zip,
            @Field("status") String status
    );
}
