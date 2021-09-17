package com.example.finalexam.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalexam.MovieDetailsActivity;
import com.example.finalexam.R;
import com.example.finalexam.database.Database;
import com.example.finalexam.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieSearchAdapter extends RecyclerView.Adapter<MovieSearchAdapter.ViewHolder> {
    Context context;
    private ArrayList<Movie> movieArrayList;
    private Database db;

    public MovieSearchAdapter(Context context, ArrayList<Movie> movieArrayList)  {
        this.movieArrayList = movieArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_res_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Context context = holder.moviePoster.getContext();
        Picasso.get().load(movieArrayList.get(position).getMoviePoster()).into(holder.moviePoster);
        holder.movieTitle.setText(movieArrayList.get(position).getMovieTitle()
        );

        holder.btnDetails.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtra("Movie", movieArrayList.get(position));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    @Override
    public int getItemViewType(final int position) {

        return R.layout.movie_res_item;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView moviePoster;
        TextView movieTitle;
        Button btnDetails;
        LinearLayout layoutMovieResList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.res_item_poster);
            movieTitle = itemView.findViewById(R.id.res_item_title);
            layoutMovieResList = itemView.findViewById(R.id.res_item_movie_list);
            btnDetails = itemView.findViewById(R.id.res_item_btn_detail);
        }
    }


}
