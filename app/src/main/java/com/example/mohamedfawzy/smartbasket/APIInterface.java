package com.example.mohamedfawzy.smartbasket;

import java.util.List;

import POJO.Question;
import POJO.Student;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Mohamed Fawzy on 3/30/2017.
 */
public interface APIInterface {

    @GET("api/getStudents")
    Call<List<Student>> getStudents();

    @GET("api/getStudentNameById?")
    Call <Student> getStudentById(@Query("id") int id);

    @GET("api/getQuestionForStudent?")
    Call <List<Question>> getQuestionForStudent(@Query("school_id") int school_id, @Query("level") int level, @Query("subject") String subject);

    @GET("api/AddScore?")
    Call <Void> AddScore(@Query("id") int id, @Query("newscore") int newscore);
}
