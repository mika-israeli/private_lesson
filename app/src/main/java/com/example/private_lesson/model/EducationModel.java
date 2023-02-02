package com.example.private_lesson.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.private_lesson.EditPostFragment;
import com.example.private_lesson.model.Education;
import com.example.private_lesson.model.EducationApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EducationModel {

    private final static EducationModel _instance = new EducationModel();
    public static EducationModel instance(){
        return _instance;
    }

    final String BASE_URL = "https://www.omdbapi.com/";
    Retrofit retrofit;
    EducationApi educationApi;


    private EducationModel() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        educationApi = retrofit.create(EducationApi.class);

    }


    public LiveData<List<Education>> searchEducationByTitle(String title) {
        MutableLiveData<List<Education>> data = new MutableLiveData<>();
        Call<EducationSerachResult> call = educationApi.searchEducationByTitle(title);
        call.enqueue(new Callback<EducationSerachResult>() {
            @Override
            public void onResponse(Call<EducationSerachResult> call, Response<EducationSerachResult> response) {
                if (response.isSuccessful()) {
                    EducationSerachResult res = response.body();
                    data.setValue(res.getSearch());
                } else {
                    Log.d("TAG", " error");
                }
            }

            @Override
            public void onFailure(Call<EducationSerachResult> call, Throwable t) {
                Log.d("TAG", "error");
            }
        });
        return data;


    }
}
