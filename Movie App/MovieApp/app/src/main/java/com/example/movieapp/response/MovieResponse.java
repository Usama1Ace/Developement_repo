package com.example.movieapp.response;

// This Class is for Single Movie Request......

import com.example.movieapp.Model.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// Gson have that Serializing.....
//Use Serializing Object so Java Compiler will understand the code..
public class MovieResponse {
//    1- Finding Movie Object
@SerializedName("results")
    @Expose
    private MovieModel movie;

    public MovieModel getMovie(){
        return movie;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "movie=" + movie +
                '}';
    }
}
