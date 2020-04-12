package com.example.navitruck.callback;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public interface LoginCallBack extends  Callback<ResponseBody>{

    void onResponse(Call<ResponseBody> call, Response<ResponseBody> response);

    void onFailure(Call<ResponseBody> call, Throwable t);
}
