package com.example.movieapp.request;

import android.util.Log;

import com.example.movieapp.utils.Credentials;
import com.example.movieapp.utils.MovieApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.GsonBuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


// Singleton Pattren for Retrofit API.......


public class Service {

    private  static Retrofit.Builder retrofitBuilder=
            new Retrofit.Builder()
            .baseUrl(Credentials.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

//    Singleton Arch
    private static Retrofit retrofit=retrofitBuilder.build();

//    interface
    private static MovieApi movieApi=retrofit.create(MovieApi.class);


    public static MovieApi getMovieApi() {
        return movieApi;
    }
}
