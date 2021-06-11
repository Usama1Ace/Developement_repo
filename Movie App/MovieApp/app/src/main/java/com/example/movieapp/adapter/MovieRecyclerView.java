package com.example.movieapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.Model.MovieModel;
import com.example.movieapp.R;
import com.example.movieapp.utils.Credentials;

import java.util.List;

public class MovieRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

//  THese ints are used for switching between different views in Recyclerview
    private static final int DISPLAY_POP=1;
    private static final int DISPLAY_SEARCH=2;

    private List<MovieModel> mMovies;
    private OnMovieListener onMovieListener;

    public MovieRecyclerView(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
//
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item,
//                parent, false);
//
//        return new MovieViewHolder(view, onMovieListener);

        
        
        View view=null;
        if(viewType==DISPLAY_SEARCH){
            view=LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item,parent,false);
            return new MovieViewHolder(view,onMovieListener);


        }else{
            view=LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_movie_list_item,parent,false);
            return new Popular_Movie_ViewHolder(view,onMovieListener);

        }
        
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull RecyclerView.ViewHolder holder, int i) {

        int itemviewType=getItemViewType(i);
        if(itemviewType == DISPLAY_SEARCH){

            ((MovieViewHolder) holder).title.setText(mMovies.get(i).getTitle());
            ((MovieViewHolder) holder).release_date.setText(mMovies.get(i).getRelease_date());


//        There is an error for Runtime attribute
//        ((MovieViewHolder)holder).duration.setText(""+mMovies.get(i).getVote_average());

            ((MovieViewHolder) holder).duration.setText(mMovies.get(i).getoriginal_language());


//        Voting average is over 10, and our rating bar is over 5 stars: Dividing by 2
            ((MovieViewHolder) holder).ratingBar.setRating((mMovies.get(i).getVote_average()) / 2);


//        Glide for Images
            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500/" + mMovies.get(i).getPoster_path())
                    .into(((MovieViewHolder) holder).imageView);


        }else{


            ((Popular_Movie_ViewHolder) holder).title.setText(mMovies.get(i).getTitle());
            ((Popular_Movie_ViewHolder) holder).release_date.setText(mMovies.get(i).getRelease_date());


//        There is an error for Runtime attribute
//        ((MovieViewHolder)holder).duration.setText(""+mMovies.get(i).getVote_average());

            ((Popular_Movie_ViewHolder) holder).duration.setText(mMovies.get(i).getoriginal_language());


//        Voting average is over 10, and our rating bar is over 5 stars: Dividing by 2
            ((Popular_Movie_ViewHolder) holder).ratingBar.setRating((mMovies.get(i).getVote_average()) / 2);


//        Glide for Images
            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500/" + mMovies.get(i).getPoster_path())
                    .into(((Popular_Movie_ViewHolder) holder).imageView);

        }



//        We need to get the runtime & the category
//        We need to change the api


    }

    @Override
    public int getItemCount() {
        if (mMovies != null) {
            return mMovies.size();
        }
        return 0;
    }

    public void setmMovies(List<MovieModel> mMovies) {
        this.mMovies = mMovies;
        notifyDataSetChanged();
    }


    //    Getting the ID of Movie Clicked...
    public MovieModel getSelectedMovie(int position) {
        if (mMovies != null) {
            if (mMovies.size() > 0) {
                return mMovies.get(position);
            }
        }
        return null;
    }


    @Override
    public int getItemViewType(int position) {
        if(Credentials.POPULAR)
        {
                return DISPLAY_POP;
        }else{

            return DISPLAY_SEARCH;
        }

    }
}
