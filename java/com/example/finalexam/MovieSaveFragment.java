package com.example.finalexam;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.finalexam.adapters.MovieSaveAdapter;
import com.example.finalexam.database.Database;
import com.example.finalexam.models.Movie;

import java.util.ArrayList;

public class MovieSaveFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private ArrayList<Movie> movieArrayList = new ArrayList<>();
    private Database db;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_movie_save, container, false);
        setHasOptionsMenu(true);
        getActivity().setTitle("Saved Movies");

        recyclerView = view.findViewById(R.id.saved_rc_list);

        db = new Database(getContext());
        Cursor savedMusicData = db.getSavedMusic();
        if (savedMusicData.getCount() == 0) {
            Toast.makeText(getContext(),"No saved Movies.",Toast.LENGTH_SHORT).show();
        } else {
            while (savedMusicData.moveToNext()) {
                String movieID = savedMusicData.getString(1);
                Log.d("Saved Movie ID", movieID);
                Cursor movie = db.getMovieByID(movieID);
                movie.moveToFirst();
                movieArrayList.add(new Movie(
                        movie.getString(0),
                        movie.getString(1),
                        movie.getString(2),
                        movie.getString(3)));
            }
            MovieSaveAdapter movieSaveAdapter = new MovieSaveAdapter(getContext(), movieArrayList);
            recyclerView.setAdapter(movieSaveAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setHasFixedSize(true);
        }

        return view;
    }


}