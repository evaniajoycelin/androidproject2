package com.example.finalexam.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalexam.MovieDetailsActivity;
import com.example.finalexam.MovieSavedDetailActivity;
import com.example.finalexam.R;
import com.example.finalexam.database.Database;
import com.example.finalexam.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieSaveAdapter extends RecyclerView.Adapter<MovieSaveAdapter.ViewHolder> {
    Context context;
    private ArrayList<Movie> movieArrayList;
    private Database db;

    public MovieSaveAdapter(Context context, ArrayList<Movie> movieArrayList)  {
        this.movieArrayList = movieArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_saved_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Context context = holder.moviePoster.getContext();
        Picasso.get().load(movieArrayList.get(position).getMoviePoster()).into(holder.moviePoster);
        holder.movieTitle.setText(movieArrayList.get(position).getMovieTitle()
        );

        holder.btnDetails.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieSavedDetailActivity.class);
            intent.putExtra("savedMovie", movieArrayList.get(position));
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
        return R.layout.movie_saved_item;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView moviePoster;
        TextView movieTitle;
        Button btnDetails;
        LinearLayout layoutMovieSaveList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.save_item_poster);
            movieTitle = itemView.findViewById(R.id.save_item_title);
            btnDetails = itemView.findViewById(R.id.save_item_btn_detail);
            layoutMovieSaveList = itemView.findViewById(R.id.saved_rc_list);
        }
    }
}
