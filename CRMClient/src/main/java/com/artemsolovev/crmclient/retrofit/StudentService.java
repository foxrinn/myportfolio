package com.artemsolovev.crmclient.retrofit;

import com.artemsolovev.crmclient.dto.ResponseResult;
import com.artemsolovev.crmclient.model.Student;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface StudentService {
    @POST("{idTeacher}")
    Call<ResponseResult<Student>> post(@Body Student student, @Path("idTeacher") long idTeacher);

    @GET(".")
    Call<ResponseResult<List<Student>>> getAll();

    @GET("all/{idTeacher}")
    Call<ResponseResult<List<Student>>> getByTeacher(@Path("idTeacher") long idTeacher);

    @GET("{id}")
    Call<ResponseResult<Student>> get(@Path("id") long id);

    @PUT(".")
    Call<ResponseResult<Student>> put(@Body Student student);
}
