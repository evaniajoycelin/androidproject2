package com.example.finalexam.models;

public class SavedMovie {
    private int saveID;
    private String movieID;

    public SavedMovie(int saveID, String movieID) {
        this.saveID = saveID;
        this.movieID = movieID;
    }

    public int getSaveID() {
        return saveID;
    }

    public void setSaveID(int saveID) {
        this.saveID = saveID;
    }

    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }
}
