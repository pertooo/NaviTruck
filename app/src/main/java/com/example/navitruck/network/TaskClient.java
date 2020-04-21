package com.example.navitruck.network;

import com.example.navitruck.callback.TaskAcceptCallback;
import com.example.navitruck.dto.abst.AbstractDTO;
import com.example.navitruck.dto.response.ResponseTaskDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TaskClient {

    @POST("/task/accept")
    Call<ResponseTaskDTO<Object>> accept(
            @Query("taskId") long taskId,
            @Query("userId") long userId,
            @Query("fee") double fee
    );
}
