package com.example.android.popularmovies;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


import static com.example.android.popularmovies.BuildConfig.ApiKey;

public class MainActivity extends AppCompatActivity {
    private RecyclerView moviesRecyclerView;
    private MoviesAdapter moviesAdapter;

    String apiKey = ApiKey;

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesRecyclerView = findViewById(R.id.recyclerView);
        int col = getResources().getInteger(R.integer.span_count);
        moviesRecyclerView.setLayoutManager(new GridLayoutManager(this, col));
        moviesAdapter = new MoviesAdapter(this);
        moviesRecyclerView.setAdapter(moviesAdapter);

        if (!isOnline()) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "No connection, you're offline", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            getPopularMovies();
        }
    }

    //=========================== GET POPULAR MOVIES ================================
    private void getPopularMovies() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", ApiKey);
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        MovieAPI_Interface service = restAdapter.create(MovieAPI_Interface.class);
        service.getPopularMovies(new Callback<Movie.MovieResult>() {
            @Override
            public void success(Movie.MovieResult movieResult, Response response) {
                moviesAdapter.setMovieList(movieResult.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });

    }

    //============================ GET TOP RATED =================================
    private void getTopRated() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", ApiKey);
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        MovieAPI_Interface service = restAdapter.create(MovieAPI_Interface.class);
        service.getTopRated(new Callback<Movie.MovieResult>() {
            @Override
            public void success(Movie.MovieResult movieResult, Response response) {
                moviesAdapter.setMovieList(movieResult.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }


    //=================================== MENU ==================================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.popular:
                getPopularMovies();
                return true;
            case R.id.top_rated:
                getTopRated();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public MovieViewHolder(View itemview) {
            super(itemview);
            imageView = itemview.findViewById(R.id.imageView);
        }
    }

    public static class MoviesAdapter extends RecyclerView.Adapter<MovieViewHolder> {
        private List<Movie> movieList;
        private LayoutInflater inflater;
        private Context context;

        public MoviesAdapter(Context context) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public MovieViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
            View view = inflater.inflate(R.layout.single_movie, parent, false);
            final MovieViewHolder viewHolder = new MovieViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = viewHolder.getAdapterPosition();
                    Intent intent = new Intent(context, DetailedActivity.class);
                    intent.putExtra(DetailedActivity.INTENT, movieList.get(position));
                    context.startActivity(intent);
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MovieViewHolder holder, int position) {
            Movie movie = movieList.get(position);
            Picasso.with(context)
                    .load(movie.getPoster())
                    .placeholder(R.color.sky)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return (movieList == null) ? 0 : movieList.size();
        }

        public void setMovieList(List<Movie> movieList) {
            this.movieList = new ArrayList<>();
            this.movieList.addAll(movieList);
            notifyDataSetChanged();
        }
    }
}
