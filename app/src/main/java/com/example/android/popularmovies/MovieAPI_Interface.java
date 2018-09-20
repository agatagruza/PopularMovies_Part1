package com.example.android.popularmovies;

import retrofit.Callback;
import retrofit.http.GET;

public interface MovieAPI_Interface {

    @GET("/movie/popular")
    void getPopularMovies(Callback<Movie.MovieResult> cb);

    @GET("/movie/top_rated")
    void getTopRated(Callback<Movie.MovieResult> cb);

}
