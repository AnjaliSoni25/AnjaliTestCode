package com.example.codingtest.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.codingtest.repo.MovieRepository;

public class MovieViewModelFactory implements ViewModelProvider.Factory {

    private MovieRepository movieRepository;

    public MovieViewModelFactory(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainActivityViewModel.class)) {
            //noinspection unchecked
            return (T) new MainActivityViewModel(movieRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}