package com.example.navitruck.network;

import com.example.navitruck.dto.UserDto;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HTTP;

public interface AuthenticateClient {

 @HTTP(method = "POST", path = "/login", hasBody = true)
 Call<ResponseBody> authenticate(@Body UserDto userDto);


}
