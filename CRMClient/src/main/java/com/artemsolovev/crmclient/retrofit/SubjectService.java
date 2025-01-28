package com.artemsolovev.crmclient.retrofit;

import com.artemsolovev.crmclient.dto.ResponseResult;
import com.artemsolovev.crmclient.model.Lesson;
import com.artemsolovev.crmclient.model.Subject;
import retrofit2.Call;
import retrofit2.http.*;

import java.time.LocalTime;
import java.util.List;

public interface SubjectService {

    @POST("{idTeacher}")
    Call<ResponseResult<Subject>> post(@Body Subject subject, @Path("idTeacher") long idTeacher);

    @GET("{id}")
    Call<ResponseResult<Subject>> get(@Path("id") long id);

    @GET("all/{idTeacher}")
    Call<ResponseResult<List<Subject>>> getByTeacher(@Path("idTeacher") long idTeacher);

    @DELETE("{id}")
    Call<ResponseResult<Subject>> delete(@Path("id") long id);
}
