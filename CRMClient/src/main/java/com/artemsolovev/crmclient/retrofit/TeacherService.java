package com.artemsolovev.crmclient.retrofit;

import com.artemsolovev.crmclient.dto.ResponseResult;
import com.artemsolovev.crmclient.model.Teacher;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface TeacherService {
    @POST(".")
    Call<ResponseResult<Teacher>> post(@Body Teacher teacher);

    @GET(".")
    Call<ResponseResult<List<Teacher>>> getAll();

    @GET("{id}")
    Call<ResponseResult<Teacher>> get(@Path("id") long id);

    @PUT(".")
    Call<ResponseResult<Teacher>> put(@Body Teacher teacher);
}
