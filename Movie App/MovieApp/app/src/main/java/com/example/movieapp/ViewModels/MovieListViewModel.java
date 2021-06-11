package com.example.movieapp.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieapp.Model.MovieModel;
import com.example.movieapp.Repositories.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {

//    This class is used for ViewModel

//    Acts as class of LiveData
//    LiveData
//    private MutableLiveData<List<MovieModel>> mMovies= new MutableLiveData<>();



    private MovieRepository movieRepository;


//    Constructor


    public MovieListViewModel() {
        movieRepository=MovieRepository.getInstance();
    }

//    Getters
    public LiveData<List<MovieModel>> getMovies(){
//        return mMovies;
        return movieRepository.getMovies();
    }


    //    Getters
    public LiveData<List<MovieModel>> getPop(){
//        return mMovies;
        return movieRepository.getPop();
    }



//  3-  Calling the method searchMoviesApi() from MovieApiClient to MovieListViewModel
    public void searchMoviesApi(String query, int pageNumber){
        movieRepository.searchMoviesApi(query,pageNumber);
    }


    //  3-  Calling the method searchMoviesApi() from MovieApiClient to MovieListViewModel
    public void searchMoviesPop(int pageNumber){
        movieRepository.searchMoviesPop(pageNumber);
    }

//  This will call searchmovie Api method above and passing different parameters.

    public void searchNextPage(){
        movieRepository.searchNextpage();
    }
}
