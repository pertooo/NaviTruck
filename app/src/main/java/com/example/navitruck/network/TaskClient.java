package com.example.navitruck.network;

import com.example.navitruck.callback.TaskAcceptCallback;
import com.example.navitruck.dto.abst.AbstractDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TaskClient {

    @POST("/task/accept")
    Call<AbstractDTO> accept(
            @Query("taskid") long taskId,
            @Query("fee") double fee
    );
}
