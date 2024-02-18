package com.example.codingtest.movie;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.codingtest.R;
import com.example.codingtest.databinding.ItemGridBinding;
import com.example.codingtest.detail.DetailActivity;
import com.example.codingtest.model.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private Context context;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }
    public void addMovies(List<Movie> newMovies) {
        int startPosition = movies.size();
        movies.addAll(newMovies);
        notifyItemRangeInserted(startPosition, newMovies.size());
    }
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemGridBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_grid, parent, false);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);

        Glide.with(context)
                .load(movie.getPosterPath())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.binding.ivList);


        holder.binding.llMain.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("MOVIE_EXTRA", (Parcelable) movie);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return movies != null ? movies.size() : 0;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        private ItemGridBinding binding;

        public MovieViewHolder(@NonNull ItemGridBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}