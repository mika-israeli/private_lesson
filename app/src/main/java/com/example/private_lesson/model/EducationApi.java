package com.example.private_lesson.model;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EducationApi {

    @GET("/?&apikey=c86677ee")
    Call<EducationSerachResult>searchEducationByTitle(@Query("s")String title);

    @GET("/?&apikey=c86677ee")
    Call<Education>getEducationByTitle(@Query("t")String title);



}
