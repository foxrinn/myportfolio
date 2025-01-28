package com.artemsolovev.crmclient.retrofit;

import com.artemsolovev.crmclient.dto.ResponseResult;
import com.artemsolovev.crmclient.model.Lesson;
import retrofit2.Call;
import retrofit2.http.*;

import java.time.LocalTime;
import java.util.List;

public interface LessonService {
    @POST(".")
    Call<ResponseResult<Lesson>> post(@Body Lesson lesson, @Query("idTeacher") long idTeacher,
                                      @Query("idStudent") long idStudent, @Query("idSubject") long idSubject);

    @GET("{id}")
    Call<ResponseResult<Lesson>> get(@Path("id") long id);

    @GET("student/{id}")
    Call<ResponseResult<List<Lesson>>> getByStudent(@Path("id") long id);

    @GET("date")
    Call<ResponseResult<List<LocalTime>>> get(@Query("idTeacher") long idTeacher, @Query("localDate") String localDate);

    @GET(".")
    Call<ResponseResult<List<Lesson>>> getByTeacher(@Query("idTeacher") long idTeacher);

    @DELETE("{id}")
    Call<ResponseResult<Lesson>> delete(@Path("id") long id);
}
