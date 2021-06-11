package com.example.movieapp.adapter;


//This listener will acts as a Item CLick Listener in List
public interface OnMovieListener {
    void onMovieClick(int position);
    void onCategoryClick(String category);

}
