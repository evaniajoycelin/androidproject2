package com.example.finalexam.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private String movieID;
    private String movieTitle;
    private String movieYear;
    private String moviePoster;


    public Movie(String movieID, String movieTitle, String movieYear, String moviePoster) {
        this.movieID = movieID;
        this.movieTitle = movieTitle;
        this.movieYear = movieYear;
        this.moviePoster = moviePoster;
    }

    protected Movie(Parcel in) {
        movieID = in.readString();
        movieTitle = in.readString();
        movieYear = in.readString();
        moviePoster = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieYear() {
        return movieYear;
    }

    public void setMovieYear(String movieYear) {
        this.movieYear = movieYear;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(movieID);
        parcel.writeString(movieTitle);
        parcel.writeString(movieYear);
        parcel.writeString(moviePoster);

    }
}
