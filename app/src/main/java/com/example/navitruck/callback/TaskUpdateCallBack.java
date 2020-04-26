package com.example.navitruck.callback;

import com.example.navitruck.dto.response.ResponseTaskDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public interface TaskUpdateCallBack extends Callback<ResponseTaskDTO<Object>> {

    void onResponse(Call<ResponseTaskDTO<Object>> call, Response<ResponseTaskDTO<Object>> response);

    void onFailure(Call<ResponseTaskDTO<Object>> call, Throwable t);
}
