package com.artemsolovev.crmclient.retrofit;

import com.artemsolovev.crmclient.dto.ResponseResult;
import com.artemsolovev.crmclient.model.Teacher;
import com.artemsolovev.crmclient.util.Constants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;

public class TeacherRepository {

    private ObjectMapper objectMapper;
    private TeacherService service;

    public TeacherRepository() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL + "teacher/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(client)
                .build();
        this.service = retrofit.create(TeacherService.class);
    }

    public TeacherRepository(String login, String password) {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new BasicAuthInterceptor(login, password)).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL + "teacher/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(client)
                .build();
        this.service = retrofit.create(TeacherService.class);
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

    public Teacher post(Teacher teacher) throws IOException {
        Response<ResponseResult<Teacher>> execute = this.service.post(teacher).execute();
        return getData(execute);
    }

    public List<Teacher> getAll() throws IOException {
        return service.getAll().execute().body().getData();
    }

    public Teacher get(long id) throws IOException {
        Response<ResponseResult<Teacher>> execute = service.get(id).execute();
        return getData(execute);
    }

    public Teacher put(Teacher teacher) throws IOException {
        Response<ResponseResult<Teacher>> execute = this.service.put(teacher).execute();
        return getData(execute);
    }
}
