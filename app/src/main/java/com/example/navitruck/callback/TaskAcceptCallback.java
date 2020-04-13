package com.example.navitruck.callback;

import com.example.navitruck.dto.abst.AbstractDTO;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public interface TaskAcceptCallback extends Callback<AbstractDTO> {

    void onResponse(Call<AbstractDTO> call, Response<AbstractDTO> response);

    void onFailure(Call<AbstractDTO> call, Throwable t);
}
