package com.example.movieapp.response;

import com.example.movieapp.Model.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

// THis class is for Getting Multiples Movies (Movies List) - Popular Movies....
//First Serializing and then DeSerializing....
public class MovieSearchResponse {

    @SerializedName("total_results")
    @Expose
    private int total_count;

     @SerializedName("results")
    @Expose
    private List<MovieModel> movies;

//Singleton Pattren....
     public int getTotal_count(){
         return total_count;

     }

     public List<MovieModel> getMovies(){
         return movies;
     }

    @Override
    public String toString() {
        return "MovieSearchResponse{" +
                "total_count=" + total_count +
                ", movies=" + movies +
                '}';
    }
}
