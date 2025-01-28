package com.artemsolovev.crmclient.retrofit;

import com.artemsolovev.crmclient.dto.ResponseResult;
import com.artemsolovev.crmclient.model.WorkDaySchedule;
import com.artemsolovev.crmclient.util.Constants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

public class WorkDayScheduleRepository {
    private ObjectMapper objectMapper;
    private WorkDayScheduleService service;

    public WorkDayScheduleRepository() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL + "teacherschedule/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(client)
                .build();
        this.service = retrofit.create(WorkDayScheduleService.class);
    }

    public WorkDayScheduleRepository(String login, String password) {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new BasicAuthInterceptor(login, password)).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL + "workdayschedule/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(client)
                .build();
        this.service = retrofit.create(WorkDayScheduleService.class);
    }

    private <T> T getData(Response<ResponseResult<T>> execute) throws IOException {
        if(execute.code() != 200){
            String message = objectMapper.readValue(execute.errorBody().string(),
                    new TypeReference<ResponseResult<T>>() {
                    }).getMessage();
            throw new IllegalArgumentException(message);
        }
        return execute.body().getData();
    }

    public WorkDaySchedule post(WorkDaySchedule workDaySchedule, long idTeacher) throws IOException {
        Response<ResponseResult<WorkDaySchedule>> execute = this.service.post(workDaySchedule, idTeacher).execute();
        return getData(execute);
    }

    public WorkDaySchedule get(long id) throws IOException {
        Response<ResponseResult<WorkDaySchedule>> execute = service.get(id).execute();
        return getData(execute);
    }

    public List<WorkDaySchedule> getByTeacher(long idTeacher) throws IOException {
        Response<ResponseResult<List<WorkDaySchedule>>> execute = service.getByTeacher(idTeacher).execute();
        return getData(execute);
    }

    public WorkDaySchedule delete(long id) throws IOException {
        Response<ResponseResult<WorkDaySchedule>> execute = service.delete(id).execute();
        return getData(execute);
    }
}
