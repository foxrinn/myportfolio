package com.artemsolovev.crmclient.retrofit;

import com.artemsolovev.crmclient.dto.ResponseResult;
import com.artemsolovev.crmclient.model.WorkDaySchedule;
import retrofit2.Call;
import retrofit2.http.*;

import java.time.LocalTime;
import java.util.List;

public interface WorkDayScheduleService {
    @POST("{idTeacher}")
    Call<ResponseResult<WorkDaySchedule>> post(@Body WorkDaySchedule workDaySchedule, @Path("idTeacher") long idTeacher);

    @GET("{id}")
    Call<ResponseResult<WorkDaySchedule>> get(@Path("id") long id);

    @GET("teacher/{idTeacher}")
    Call<ResponseResult<List<WorkDaySchedule>>> getByTeacher(@Path("idTeacher") long idTeacher);

    @DELETE("{id}")
    Call<ResponseResult<WorkDaySchedule>> delete(@Path("id") long id);
}
