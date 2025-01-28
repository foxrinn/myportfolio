package com.artemsolovev.crmclient.retrofit;

import com.artemsolovev.crmclient.dto.ResponseResult;
import com.artemsolovev.crmclient.model.Lesson;
import com.artemsolovev.crmclient.util.Constants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LessonRepository {

    private ObjectMapper objectMapper;
    private LessonService service;

    public LessonRepository() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL + "lesson/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(client)
                .build();
        this.service = retrofit.create(LessonService.class);
    }

    public LessonRepository(String login, String password) {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new BasicAuthInterceptor(login, password)).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL + "lesson/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(client)
                .build();
        this.service = retrofit.create(LessonService.class);
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

    public Lesson post(Lesson lesson, long idTeacher, long idStudent, long idSubject) throws IOException {
        Response<ResponseResult<Lesson>> execute = this.service.post(lesson, idTeacher, idStudent, idSubject).execute();
        return getData(execute);
    }

    public Lesson get(long id) throws IOException {
        Response<ResponseResult<Lesson>> execute = service.get(id).execute();
        return getData(execute);
    }

    public List<Lesson> getByTeacher(long idTeacher) throws IOException {
        Response<ResponseResult<List<Lesson>>> execute = service.getByTeacher(idTeacher).execute();
        return getData(execute);
    }

    public List<Lesson> getByStudent(long idStudent) throws IOException {
        Response<ResponseResult<List<Lesson>>> execute = service.getByStudent(idStudent).execute();
        return getData(execute);
    }

    public List<LocalTime> get(long idTrainer, LocalDate localDate) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Response<ResponseResult<List<LocalTime>>> execute = service.get(idTrainer, formatter.format(localDate)).execute();
        return getData(execute);
    }

    public Lesson delete(long id) throws IOException {
        Response<ResponseResult<Lesson>> execute = service.delete(id).execute();
        return getData(execute);
    }
}
