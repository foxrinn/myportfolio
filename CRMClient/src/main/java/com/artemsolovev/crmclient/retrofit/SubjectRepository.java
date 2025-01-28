package com.artemsolovev.crmclient.retrofit;

import com.artemsolovev.crmclient.dto.ResponseResult;
import com.artemsolovev.crmclient.model.Student;
import com.artemsolovev.crmclient.model.Subject;
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

public class SubjectRepository {

    private ObjectMapper objectMapper;
    private SubjectService service;

    public SubjectRepository() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL + "subject/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(client)
                .build();
        this.service = retrofit.create(SubjectService.class);
    }

    public SubjectRepository(String login, String password) {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new BasicAuthInterceptor(login, password)).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL + "subject/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(client)
                .build();
        this.service = retrofit.create(SubjectService.class);
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

    public Subject post(Subject subject, long idTeacher) throws IOException {
        Response<ResponseResult<Subject>> execute = this.service.post(subject, idTeacher).execute();
        return getData(execute);
    }

    public List<Subject> getByTeacher(long idTeacher) throws IOException {
        return service.getByTeacher(idTeacher).execute().body().getData();
    }

    public Subject get(long id) throws IOException {
        Response<ResponseResult<Subject>> execute = service.get(id).execute();
        return getData(execute);
    }

    public Subject delete(long id) throws IOException {
        Response<ResponseResult<Subject>> execute = service.delete(id).execute();
        return getData(execute);
    }
}
