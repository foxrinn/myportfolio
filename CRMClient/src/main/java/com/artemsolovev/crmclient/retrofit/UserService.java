package com.artemsolovev.crmclient.retrofit;

import com.artemsolovev.crmclient.dto.ResponseResult;
import retrofit2.Call;
import com.artemsolovev.crmclient.model.User;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {

    @GET("search")
    Call<ResponseResult<User>> get();

    @GET("{id}")
    Call<ResponseResult<User>> get(@Path("id") long id);
}
