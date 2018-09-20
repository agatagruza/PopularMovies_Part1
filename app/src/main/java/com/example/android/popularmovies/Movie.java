package com.example.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie implements Parcelable {
    @SerializedName("original_title")
    private String title;
    @SerializedName("overview")
    private String description;
    @SerializedName("poster_path")
    private String poster;
    @SerializedName("release_date")
    private String release_date;
    @SerializedName("vote_average")
    private float VoteAverage;

    public Movie() {
    }

    //protected vs private?
    //constructor
    protected Movie(Parcel in) {
        title = in.readString();
        poster = in.readString();
        description = in.readString();
        release_date = in.readString();
        VoteAverage = in.readFloat();
    }

    //Two arguments- Parcel object and "i". "i" are good for arrays and positions
    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(title);
        dest.writeString(poster);
        dest.writeString(description);
        dest.writeString(release_date);
        dest.writeFloat(VoteAverage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //Interface. It generates instances of parcelable class
    //It will unwrapped parcelable class and read it properties
    //"Creator" will create "CREATOR" object of type Movie
    //Creator receiving Parcel and trying to decode it
    //It will override createFromParcel and newArray methods
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        //Both @Override functions required by Parcelable
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
            //Will return private constructor define above
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return "http://image.tmdb.org/t/p/w500" + poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String getReleaseDate() {
        return release_date;
    }

    public void setReleaseDate(String release_date) {
        this.release_date = release_date;
    }

    public float getVoteAverage() {
        return VoteAverage;
    }

    public void setVoteAverage(float VoteAverage) {
        this.VoteAverage = VoteAverage;
    }

    public static class MovieResult {
        private List<Movie> results;

        public List<Movie> getResults() {
            return results;
        }
    }
}
