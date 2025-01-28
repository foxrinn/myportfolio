package com.artemsolovev.crmclient.retrofit;
import com.artemsolovev.crmclient.dto.ResponseResult;
import com.artemsolovev.crmclient.model.Student;
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

public class StudentRepository {

    private ObjectMapper objectMapper;
    private StudentService service;

    public StudentRepository() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL + "student/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(client)
                .build();
        this.service = retrofit.create(StudentService.class);
    }

    public StudentRepository(String login, String password) {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new BasicAuthInterceptor(login, password)).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL + "student/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(client)
                .build();
        this.service = retrofit.create(StudentService.class);
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

    public Student post(Student student, long idTeacher) throws IOException {
        Response<ResponseResult<Student>> execute = this.service.post(student, idTeacher).execute();
        return getData(execute);
    }

    public List<Student> getAll() throws IOException {
        return service.getAll().execute().body().getData();
    }

    public List<Student> getByTeacher(long idTeacher) throws IOException {
        return service.getByTeacher(idTeacher).execute().body().getData();
    }

    public Student get(long id) throws IOException {
        Response<ResponseResult<Student>> execute = service.get(id).execute();
        return getData(execute);
    }

    public Student put(Student student) throws IOException {
        Response<ResponseResult<Student>> execute = this.service.put(student).execute();
        return getData(execute);
    }
}
