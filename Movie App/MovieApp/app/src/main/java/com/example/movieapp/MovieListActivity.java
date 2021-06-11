package com.example.movieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.movieapp.Model.MovieModel;
import com.example.movieapp.ViewModels.MovieListViewModel;
import com.example.movieapp.adapter.MovieRecyclerView;
import com.example.movieapp.adapter.OnMovieListener;
import com.example.movieapp.request.Service;
import com.example.movieapp.response.MovieSearchResponse;
import com.example.movieapp.utils.Credentials;
import com.example.movieapp.utils.MovieApi;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity implements OnMovieListener {

//    Before we run our App, we need to add the Network Security Config


//    Button btn;

//    RecyclerView
   private RecyclerView recyclerView;

//    Adapter
    private MovieRecyclerView adapter;

    //    ViewModel
    private MovieListViewModel movieListViewModel;


    boolean isPopular =true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar
        Toolbar toolbar=findViewById(R.id.tool);
        setSupportActionBar(toolbar);


//        Search View implementation
        SetupSearchview();



        recyclerView=findViewById(R.id.recyclerview);

//        btn = findViewById(R.id.btn);

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

//     Set Recyclerview and Adapter
        ConfigureRecyclerView();


//        Calling the Observer
        ObserveAnyChange();

        ObservePopularMovies();

//  Getting Popular List
        movieListViewModel.searchMoviesPop(1);

//        searchMovieApi("Jack",1);

//        Testing the Method

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                GetRetroFitResponse();
//
//                searchMovieApi("Jack",1);
//            }
//        });
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                GetRetroFitResponse();
//                GetRetroFitResponseAccordingToMovieID();
//            }
//        });

    }

//    This method is for oberving Popular Movies....
    private void ObservePopularMovies() {

        movieListViewModel.getPop().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
//                Observing for any data change

//                Log.e("TagClass","Class Starts from Here ...... ");
                if(movieModels !=null){
//                    Log.e("TagClass","Class Starts from Here Movie Model is ...... "+movieModels);
                    for(MovieModel movieModel:movieModels){
//                        Get the Data in Log
//                        Log.e("TagClass","Class Starts from Here Movie Model is ...... "+movieModel.getMovie_overview());
//
//                        Log.v("Tagy","onChanged "+movieModel.getTitle());

                        adapter.setmMovies(movieModels);

                    }
                }
            }
        });
    }

    //    Getting data from Search view & Query the api to get the results (Movies)
    private void SetupSearchview() {
        final androidx.appcompat.widget.SearchView searchView=findViewById(R.id.search_view);

       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               movieListViewModel.searchMoviesApi(
//                        the Searhc string getted from searchview
                       query,
                       1
               );
               return false;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               return false;
           }
       });


//       When use click on Search view then ispopular is false
       searchView.setOnSearchClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               isPopular=false;
           }
       });

    }


    //    Create Method to Obseve Data change..
    private void ObserveAnyChange() {

        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
//                Observing for any data change

                Log.e("TagClass","Class Starts from Here ...... ");
                if(movieModels !=null){
                    Log.e("TagClass","Class Starts from Here Movie Model is ...... "+movieModels);
                    for(MovieModel movieModel:movieModels){
////                        Get the Data in Log
//                        Log.e("TagClass","Class Starts from Here Movie Model is ...... "+movieModel.getMovie_overview());
//
//                        Log.v("Tagy","onChanged "+movieModel.getTitle());
                        adapter.setmMovies(movieModels);

                    }
                }
            }
        });

    }



//    Calling searchMoviesApi() method from MovieApiClient in MovieListActivity
//    private void searchMovieApi(String query, int pageNumber)
//    {
//        movieListViewModel.searchMoviesApi(query,pageNumber );
//    }


//Intializing recyclerview and Adding the data to it.

    private void ConfigureRecyclerView(){
//        Live Data cannot be Passed Via contrustor.....
        adapter=new MovieRecyclerView(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));


//        Recyclerview Pagination
//        Loading Next Page of Api response
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {

                if(!recyclerView.canScrollVertically(1)){
//                    Here we need to display Next search results of the next Page
                    movieListViewModel.searchNextPage();
                }
            }
        });


    }

    @Override
    public void onMovieClick(int position) {
//        Toast.makeText(this,"This position  "+position,Toast.LENGTH_SHORT).show();
//        Get Parcable extra and pass it to MovieDetialActivity
//        We don,t need the position of movie in recyclerview
//        We need the ID of the Movie  in order to get detials of it.

        Intent intent=new Intent(this, MovieDetailActivity.class);
        intent.putExtra("movie",adapter.getSelectedMovie(position));
        startActivity(intent);


    }

    @Override
    public void onCategoryClick(String category) {

    }


// Get retrofit response from below method:

//    private void GetRetroFitResponse() {
////    Getting response from Retrofit First....
//
//        MovieApi movieApi = Service.getMovieApi();
//
//        Call<MovieSearchResponse> responseCall = movieApi
//                .searchMovies(
//                        Credentials.API_KEY,
//                        "Jack Reaccher",
//                        "1"
//                );
//
////        THis is Search Response .....
//
////        In order to enqueue response
//        responseCall.enqueue(new Callback<MovieSearchResponse>() {
//            @Override
//            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
//                if (response.code() == 200) {
//                    Log.v("TAG", "response " + response.body().toString());
//                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());
//
//                    for (MovieModel movie : movies) {
//                        Log.v("TAG", "The List " + movie.getRelease_date());
//
//                    }
//
//                } else {
//                    try {
//                        Log.v("TAG", "Error" + response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
//
//            }
//        });
//
//    }

//    THis Is for getting One movie by ID;


//    private void GetRetroFitResponseAccordingToMovieID() {
//        //    Getting response from Retrofit First....
//
//        MovieApi movieApi = Service.getMovieApi();
//        Call<MovieModel> responseCall = movieApi
//                .getMovie(
//                        550,
//                        Credentials.API_KEY
//                );
//
//        //        THis is Search Response .....
//
////        In order to enqueue response
//        responseCall.enqueue(new Callback<MovieModel>() {
//            @Override
//            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
//                if (response.code() == 200) {
//
//                    MovieModel movieModel = response.body();
//                    Log.v("TAG", "Response " + movieModel.getTitle());
//
//
//                } else {
//                    try {
//                        Log.v("TAG", "Error" + response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MovieModel> call, Throwable t) {
//
//            }
//        });
//
//
//    }


}


//Making Searhc movies by ID https://api.themoviedb.org/3/movie/550?api_key=64c9d1e6ed7ad148c9680bbeecf6e239  Movie ID

//After GetRetroFitResponseAccordingToMovieID()  function i am Implementing MVVM Pattren......


