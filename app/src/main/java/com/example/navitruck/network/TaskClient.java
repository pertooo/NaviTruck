package com.example.navitruck.network;

import com.example.navitruck.callback.TaskAcceptCallback;
import com.example.navitruck.dto.abst.AbstractDTO;
import com.example.navitruck.dto.response.ResponseTaskDTO;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TaskClient {

    @POST("/task/accept")
    Call<ResponseTaskDTO<Object>> accept(
            @Query("taskId") long taskId,
            @Query("userId") long userId,
            @Query("fee") double fee
    );

    @Multipart
    @PUT("/task/update_status")
    Call<ResponseBody> updateStatus(@Part("files") MultipartBody.Part[] files,
                                    @Query("taskId") long taskId,
                                    @Query("userId") long userId);

}
