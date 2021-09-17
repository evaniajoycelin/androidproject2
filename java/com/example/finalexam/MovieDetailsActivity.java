package com.example.finalexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalexam.database.Database;
import com.example.finalexam.models.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    private Button saveBtn;
    private TextView movieTitle, movieYear, movieID;
    private ImageView moviePoster;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        this.setTitle("Details Movie");

        db = new Database(this);

        saveBtn = findViewById(R.id.detail_btn_save);
        movieTitle = findViewById(R.id.detail_tv_title);
        movieYear = findViewById(R.id.detail_tv_year);
        moviePoster = findViewById(R.id.detail_iv_poster);
        movieID = findViewById(R.id.detail_tv_id);

        Movie movie = getIntent().getParcelableExtra("Movie");
        Picasso.get().load(movie.getMoviePoster()).into(moviePoster);

        movieTitle.setText(movie.getMovieTitle());
        movieYear.setText(movie.getMovieYear());
        movieID.setText(movie.getMovieID());
        saveBtn.setOnClickListener(view -> {
            Cursor checkID = db.getTransactionByMovieID(movie.getMovieID());

            if(checkID.getCount() > 0){
                Toast.makeText(MovieDetailsActivity.this, "Song already saved.", Toast.LENGTH_SHORT).show();
            }else{
                Log.d("Saved Movie ID", movie.getMovieID());
                db.insertSavedMovie(movie.getMovieID());

                Toast.makeText(MovieDetailsActivity.this, "Success save movie.", Toast.LENGTH_LONG).show();
                Intent mainActivity = new Intent(MovieDetailsActivity.this, MainActivity.class);
                mainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainActivity);
            }

        });
    }
}