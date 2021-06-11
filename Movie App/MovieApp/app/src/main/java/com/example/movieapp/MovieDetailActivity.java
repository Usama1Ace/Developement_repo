package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieapp.Model.MovieModel;

public class MovieDetailActivity extends AppCompatActivity {

    private TextView titldetails,descdetails;

    private ImageView imageViewdetails;
    private RatingBar ratingBardetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        imageViewdetails=findViewById(R.id.imageViewdetail);
        titldetails=findViewById(R.id.textView_title_details);
        descdetails=findViewById(R.id.textView_desc_detail);
        ratingBardetails=findViewById(R.id.ratingBar_detial);


        GetDataFromIntent();


    }

    private void GetDataFromIntent() {
        if(getIntent().hasExtra("movie"))
        {
            MovieModel movieModel=getIntent().getParcelableExtra("movie");
            Log.v("Tagy","incomming Intent "+movieModel.getMovie_id());
            titldetails.setText(movieModel.getTitle());
            descdetails.setText(movieModel.getMovie_overview());
            ratingBardetails.setRating((movieModel.getVote_average())/2);

            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/" +movieModel.getPoster_path())
                    .into(imageViewdetails);
        }
    }
}