package com.example.movieapp.utils;

import com.example.movieapp.Model.MovieModel;
import com.example.movieapp.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {
//    Search for Movies

    //    https://api.themoviedb.org/3/search/movie?api_key= 64c9d1e6ed7ad148c9680bbeecf6e239 &query=Jack+Reacher
    @GET("3/search/movie")
    Call<MovieSearchResponse> searchMovies(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );


    //    Get Popular Movies....
    @GET("3/movie/popular")
    Call<MovieSearchResponse> getpoular(
            @Query("api_key") String key,
            @Query("page") int page
    );

//Making Search movies by ID https://api.themoviedb.org/3/movie/550?api_key=64c9d1e6ed7ad148c9680bbeecf6e239  Movie ID
//Remember that movie_id=550  is for Jack Reacher
    @GET("3/movie/{movie_id}?")
    Call<MovieModel> getMovie(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key

    );


}
