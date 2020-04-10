package com.example.navitruck.network;

import com.example.navitruck.dto.AbstractDTO;
import com.example.navitruck.dto.UserDto;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HTTP;

public interface AuthenticateClient {

 @HTTP(method = "POST", path = "/api/signin", hasBody = true)
 Call<UserDto> authenticate(@Body UserDto userDto);

 @HTTP(method = "POST", path = "/login", hasBody = true)
 Call<ResponseBody> login(@Body UserDto userDto);


}