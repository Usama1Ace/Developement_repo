package com.example.movieapp.Repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.Model.MovieModel;
import com.example.movieapp.request.MovieApiClient;

import java.util.List;

public class MovieRepository {
//    This Class acts as repositories.....

    private static MovieRepository instance;


//    Creating Query for Search Next Pages..
    private String mQuery;
    private int mPageNumber;



////    LiveData
//    private MutableLiveData<List<MovieModel>> mMovies;
//    Link it to MovieApiClient Because LiveData is at RemoteData Source (MovieApiClient)
    private static MovieApiClient movieApiClient;


    //    Getter
//    Singleton Pattren Implemented
    public static MovieRepository getInstance(){
        if(instance==null)
        {
            instance=new MovieRepository();
        }
        return instance;
    }

    private MovieRepository(){
        movieApiClient= MovieApiClient.getInstance();
    }
    public LiveData<List<MovieModel>>  getMovies(){
        return movieApiClient.getMovies();
    }

    public LiveData<List<MovieModel>>  getPop(){
        return movieApiClient.getMoviespop();
    }


    //   2- Calling the method searchMoviesApi() from MovieApiClient in repository
    public void searchMoviesApi(String query, int pageNumber){
        mPageNumber=pageNumber;
        mQuery=query;
        movieApiClient.searchMoviesApi(query,pageNumber);

    }

    //   2- Calling the method searchMoviesPop() from MovieApiClient in repository
    public void searchMoviesPop(int pageNumber){
        mPageNumber=pageNumber;
        movieApiClient.searchMoviesPop(pageNumber);

    }

//  This will call searchmovie Api method above and passing different parameters.
    public void searchNextpage(){
        searchMoviesApi(mQuery,mPageNumber+1);
    }


}
