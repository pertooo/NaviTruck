package com.example.navitruck.callback;

import android.util.Log;

import com.example.navitruck.dto.AbstractDTO;
import com.example.navitruck.dto.UserDto;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public interface LoginCallBack extends  Callback<ResponseBody>{

    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response);

    public void onFailure(Call<ResponseBody> call, Throwable t);
}
