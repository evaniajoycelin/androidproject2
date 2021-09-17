package com.example.finalexam;

import androidx.appcompat.app.AppCompatActivity;

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

public class MovieSavedDetailActivity extends AppCompatActivity {

    private Button deleteBtn;
    private TextView movieTitle, movieYear, movieID;
    private ImageView moviePoster;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_saved_detail);
        this.setTitle("Details Movie");

        db = new Database(this);
        deleteBtn = findViewById(R.id.detail_delete_btn_delete);
        movieTitle = findViewById(R.id.detail_delete_tv_title);
        movieYear = findViewById(R.id.detail_delete_tv_year);
        moviePoster = findViewById(R.id.detail_delete_iv_poster);
        movieID = findViewById(R.id.detail_delete_tv_id);

        Movie movie = getIntent().getParcelableExtra("savedMovie");
        Picasso.get().load(movie.getMoviePoster()).into(moviePoster);

        movieTitle.setText(movie.getMovieTitle());
        movieYear.setText(movie.getMovieYear());
        movieID.setText(movie.getMovieID());

        deleteBtn.setOnClickListener(view -> {
            db.deleteSavedMovie(movie.getMovieID());

            Toast.makeText(MovieSavedDetailActivity.this, "Success delete movie.", Toast.LENGTH_LONG).show();
            Intent mainActivity = new Intent(MovieSavedDetailActivity.this, MainActivity.class);
            mainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mainActivity);
        });

    }
}