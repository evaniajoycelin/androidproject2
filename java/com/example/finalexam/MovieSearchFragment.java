package com.example.finalexam;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalexam.adapters.MovieSearchAdapter;
import com.example.finalexam.database.Database;
import com.example.finalexam.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieSearchFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private Button searchButton;
    private EditText searchInput;
    private ArrayList<Movie> movieArrayList = new ArrayList<>();
    private Database db;
    private String searchKeyword, searchKeywordUrl, URL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movie_search, container, false);
        setHasOptionsMenu(true);
        getActivity().setTitle("Movie List");

        init();

        searchButton.setOnClickListener(v -> {

            searchKeyword = searchInput.getText().toString();
            searchKeywordUrl = searchKeyword;

            String [] strArray = searchKeywordUrl.split(" ");

            for(int i=0 ; i<strArray.length-1 ; i++)
            {
                searchKeywordUrl = strArray[i]+"%20"+strArray[i+1];
            }

            Log.d("Search input", searchKeyword);
            Log.d("Search url", searchKeywordUrl);

            URL = "https://www.omdbapi.com/?s="+searchKeywordUrl+"&apikey=5c1857f3";

            db = new Database(getContext());
            Cursor movieData = db.getMovieList();
            if(movieData.getCount() == 0){
                Log.d("JSON BELUM ADA", "True");
                jsonReq();
            }else{
                while (movieData.moveToNext()){
                    Log.d("Cek search di DB", movieData.getString(4));
                    if(movieData.getString(4).equals(searchKeyword)){
                        movieArrayList.clear();
                        movieArrayList.add(new Movie(
                                movieData.getString(0),
                                movieData.getString(1),
                                movieData.getString(2),
                                movieData.getString(3)

                        ));

                        MovieSearchAdapter movieSearchAdapter = new MovieSearchAdapter(getContext(), movieArrayList);
                        recyclerView.setAdapter(movieSearchAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setHasFixedSize(true);

                        Log.d("Search Keyword Sama", "True");
                    }else if(!movieData.getString(4).equals(searchKeyword)){
                        movieArrayList.clear();
                        jsonReq();
                        Log.d("Search Keyword Beda", "True");
                        break;
                    }

                }
            }
        });

        return view;
    }

    void init(){
        searchButton = view.findViewById(R.id.search_btn_search);
        searchInput = view.findViewById(R.id.search_et_movie);
        recyclerView = view.findViewById(R.id.search_rc_list);
    }

    void jsonReq(){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray search = response.getJSONArray("Search");
                            for (int i = 0; i<search.length();i++){
                                JSONObject jsonObject = search.getJSONObject(i);

                                String movieID = jsonObject.getString("imdbID");
                                String movieTitle = jsonObject.getString("Title");
                                String movieYear = jsonObject.getString("Year");
                                String moviePoster = jsonObject.getString("Poster");

                                Movie movie = new Movie(movieID, movieTitle, movieYear, moviePoster);
                                movieArrayList.add(movie);
                                db.insertMovie(movie, searchKeyword);
                                Log.d("DBInsert", "Success!");
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                        MovieSearchAdapter movieSearchAdapter = new MovieSearchAdapter(getContext(), movieArrayList);
                        recyclerView.setAdapter(movieSearchAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setHasFixedSize(true);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ErrorRequest", error.getMessage());
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

}