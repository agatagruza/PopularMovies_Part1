package com.example.android.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailedActivity extends AppCompatActivity {

    public static final String INTENT = "movie";

    private Movie movieMovie;
    ImageView poster;
    TextView title;
    TextView vote_averageView;
    TextView description;
    TextView release_date;

    //protected vs public?
    //Method `onCreate(Bundle, PersistableBundle)`
    //is invoked when activity is being reinitialized after shutdown/reboot.
    //This method is declared in `Activity` and is inherited by `AppCompatActivity`.


    //`Activity` class also has an `onCreate(Bundle)` method which is call after activity
    //was closed/stopped. This method is also declared in `Activity`, but is overridden by
    //`AppCompatActivity` class.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        //Retrieve extended data from the intent
        if (getIntent().hasExtra(INTENT)) {
            movieMovie = getIntent().getParcelableExtra(INTENT);
        } else {
            throw new IllegalArgumentException("Input not valid. Movie parcelable required.");
        }

        float f = movieMovie.getVoteAverage();
        String userRating = Float.toString(f);
        String vote_averageText = "";
        if (!userRating.isEmpty()) {
            vote_averageText = getString(R.string.vote_average, userRating);
        }


        title = findViewById(R.id.movie_title);
        description = findViewById(R.id.movie_description);
        release_date = findViewById(R.id.release_date);
        vote_averageView = findViewById(R.id.vote_average);
        poster = findViewById(R.id.movie_poster);

        title.setText(movieMovie.getTitle());
        description.setText(movieMovie.getDescription());
        vote_averageView.setText(vote_averageText);
        String releaseDate = movieMovie.getReleaseDate();
        if (releaseDate.length() > 4) releaseDate = releaseDate.substring(0, 4);
        release_date.setText(releaseDate);

        Picasso.with(this)
                .load(movieMovie.getPoster())
                //local resource that app comes with. Loading image
                //takes a while. You need to make network request, wait for the server to respond,
                // Until image is load it will show placeholder image.
                .placeholder(R.color.sky)
                // will be displayed if the image cannot be loaded
                .error(R.mipmap.ic_launcher_round)
                .into(poster);
    }
}
