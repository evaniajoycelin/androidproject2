package com.example.finalexam.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.finalexam.models.Movie;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "final_exam.db";

    public static final String TABLE_MOVIES = "movies";
    private static final String MOVIE_TITLE = "movieTitle";
    private static final String MOVIE_YEAR = "movieYear";
    private static final String MOVIE_POSTER = "moviePoster";
    private static final String MOVIE_ID = "movieID";
    private static final String SEARCH_KEYWORD = "searchKeyword";

    public static final String TABLE_SAVED_MOVIES = "saved_movies";
    private static final String MOVIE_SAVED_ID = "saveID";

    private static final String CREATE_TABLE_MOVIES = "CREATE TABLE " + TABLE_MOVIES
            + "(" + MOVIE_ID + " TEXT PRIMARY KEY,"
            + MOVIE_TITLE + " TEXT,"
            + MOVIE_YEAR + " TEXT,"
            + MOVIE_POSTER + " TEXT ,"
            + SEARCH_KEYWORD + " TEXT" + ")";

    private static final String CREATE_TABLE_SAVED_MUSIC = "CREATE TABLE " + TABLE_SAVED_MOVIES
            + "(" + MOVIE_SAVED_ID + " INTEGER PRIMARY KEY,"
            + MOVIE_ID + " TEXT" + ")";

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_MOVIES);
        sqLiteDatabase.execSQL(CREATE_TABLE_SAVED_MUSIC);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVer, int newVer) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVED_MOVIES);

        onCreate(sqLiteDatabase);
    }

    public int insertMovie(Movie movie, String search) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MOVIE_ID, movie.getMovieID());
        contentValues.put(MOVIE_TITLE, movie.getMovieTitle());
        contentValues.put(MOVIE_YEAR, movie.getMovieYear());
        contentValues.put(MOVIE_POSTER, movie.getMoviePoster());
        contentValues.put(SEARCH_KEYWORD, search);

        sqLiteDatabase.insert(TABLE_MOVIES, null, contentValues);

        return 0;
    }

    public Cursor getMovieList(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_MOVIES, null);
        return data;
    }

    public Cursor getMovieByID(String movieID) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_MOVIES + " WHERE movieID = " + "\"" +  movieID +"\"", null);
        return data;
    }

    public int insertSavedMovie(String movieID){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MOVIE_ID, movieID);

        db.insert(TABLE_SAVED_MOVIES, null, contentValues);

        return 0;
    }

    public Cursor getSavedMusic() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_SAVED_MOVIES, null);
        return data;
    }

    public Cursor getTransactionByMovieID(String movieID){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_SAVED_MOVIES + " WHERE movieID = " + "\"" +  movieID +"\"", null);
        return data;
    }

    public void deleteSavedMovie(String movieID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_SAVED_MOVIES + " WHERE movieID = " + "\"" +  movieID +"\"");

    }

}
