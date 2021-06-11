package com.example.movieapp.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.AppExecutors;
import com.example.movieapp.Model.MovieModel;
import com.example.movieapp.Repositories.MovieRepository;
import com.example.movieapp.response.MovieSearchResponse;
import com.example.movieapp.utils.Credentials;
import com.google.gson.internal.$Gson$Preconditions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

// THis class is for  Remote Data source
//THis Client is Bridge between Retrofit Data and LiveData.
public class MovieApiClient {

    //    LiveData for searhc moveis
    private MutableLiveData<List<MovieModel>> mMovies;
    private static MovieApiClient instance;

    //    Making popular Global Runnable
    private ReteriveMoviesRunnablePop reteriveMoviesRunnablepopular;

    //    Making  Global Runnable
    private ReteriveMoviesRunnable reteriveMoviesRunnable;

    //    LiveData for popular movies
    private MutableLiveData<List<MovieModel>> mMoviespopular;


    //  Java  Singleton Pattren Implemented
    public static MovieApiClient getInstance() {
        if (instance == null) {
            instance = new MovieApiClient();
        }
        return instance;
    }

    private MovieApiClient() {
        mMovies = new MutableLiveData<>();
        mMoviespopular=new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovies() {
        return mMovies;
    }
    public LiveData<List<MovieModel>> getMoviespop() {
        return mMoviespopular;
    }


    //   We are following Architecture COmponent and Also Lower Level....So
//   1- This method that we are going to call through following classes (Repositry, ViewModel and Activity)
    public void searchMoviesApi(String query, int pageNumber) {
        if (reteriveMoviesRunnable != null) {
            reteriveMoviesRunnable = null;
        }


        reteriveMoviesRunnable = new ReteriveMoviesRunnable(query, pageNumber);

//        New Runnable Thread for Reteriving Data from RestAPI
        final Future myHandler = AppExecutors.getInstance().networkIO().submit(reteriveMoviesRunnable);


//        TimeOut 5 sec and Making Retrofit Request Cancelable
        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {

//                Canceling the Retrofit Request call

                myHandler.cancel(true);
            }
        }, 1000, TimeUnit.MILLISECONDS);

    }


    public void searchMoviesPop( int pageNumber) {
        if (reteriveMoviesRunnablepopular != null) {
            reteriveMoviesRunnablepopular = null;
        }


        reteriveMoviesRunnablepopular = new ReteriveMoviesRunnablePop(pageNumber);

//        New Runnable Thread for Reteriving Data from RestAPI
        final Future myHandler2 = AppExecutors.getInstance().networkIO().submit(reteriveMoviesRunnablepopular);


//        TimeOut 5 sec and Making Retrofit Request Cancelable
        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {

//                Canceling the Retrofit Request call

                myHandler2.cancel(true);
            }
        }, 1000, TimeUnit.MILLISECONDS);

    }


    //        Reteriving data from RestAPI by Runnable Class And Store it to LiveData
//            We have 2 types of Queries : the ID  & Search Queries
    private class ReteriveMoviesRunnable implements Runnable {

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public ReteriveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;


        }

        @Override
        public void run() {
//            Getting the response Objects
            try {
                Response response = getMovies(query, pageNumber).execute();
                if (cancelRequest) {
                    return;
                }

                if (response.code() == 200) {
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) response.body()).getMovies());
                    if (pageNumber == 1) {
//                        Sending data live Data
//                        PostValue:Used for background Thread
//                        setValue:not for background thread
                        mMovies.postValue(list);

                    } else {
                        List<MovieModel> currentmovies = mMovies.getValue();
                        currentmovies.addAll(list);
                        mMovies.postValue(currentmovies);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.v("ErrorTag", "Errors " + error);
                    mMovies.postValue(null);


                }


            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);

            }


        }

        //            Search Method/Query
        private Call<MovieSearchResponse> getMovies(String query, int pageNumbers) {
            return Service.getMovieApi().searchMovies(
                    Credentials.API_KEY,
                    query,
                    pageNumbers
            );
        }

        private void cancelRequest() {
            Log.v("Tag", "Cancelling The Request ");
            cancelRequest = true;
        }

    }


    private class ReteriveMoviesRunnablePop implements Runnable {

        private int pageNumber;
        boolean cancelRequest;

        public ReteriveMoviesRunnablePop( int pageNumber) {
            this.pageNumber = pageNumber;
            cancelRequest = false;


        }

        @Override
        public void run() {
//            Getting the response Objects
            try {
                Response response2 = getPop( pageNumber).execute();
                if (cancelRequest) {
                    return;
                }

                if (response2.code() == 200) {
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) response2.body()).getMovies());
                    if (pageNumber == 1) {
//                        Sending data live Data
//                        PostValue:Used for background Thread
//                        setValue:not for background thread
                        mMoviespopular.postValue(list);

                    } else {
                        List<MovieModel> currentmovies2 = mMoviespopular.getValue();
                        currentmovies2.addAll(list);
                        mMoviespopular.postValue(currentmovies2);
                    }
                } else {
                    String error = response2.errorBody().string();
                    Log.v("ErrorTag", "Errors " + error);
                    mMoviespopular.postValue(null);


                }


            } catch (IOException e) {
                e.printStackTrace();
                mMoviespopular.postValue(null);

            }


        }

        //            Search Method/Query
        private Call<MovieSearchResponse> getPop( int pageNumbers) {
            return Service.getMovieApi().getpoular(
                    Credentials.API_KEY,
                    pageNumbers
            );
        }

        private void cancelRequest() {
            Log.v("Tag", "Cancelling The Request ");
            cancelRequest = true;
        }

    }

}
