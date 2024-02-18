package com.example.codingtest.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.codingtest.model.Movie;
import com.example.codingtest.repo.MovieRepository;

import java.util.List;

public class MainActivityViewModel extends ViewModel {
    private MovieRepository movieRepository;
    private MutableLiveData<Integer> currentPage = new MutableLiveData<>();

    public MainActivityViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public LiveData<List<Movie>> getMovies(boolean includeAdult, boolean includeVideo, String language, int page, String sortBy) {
        return movieRepository.getMovies(includeAdult, includeVideo, language, page, sortBy);
    }


    public LiveData<List<Movie>> getMoreMovies(boolean includeAdult, boolean includeVideo, String language, String sortBy) {
        currentPage.setValue((currentPage.getValue() != null ? currentPage.getValue() : 1) + 1);
        return movieRepository.getMoreMovies(includeAdult, includeVideo, language, currentPage.getValue(), sortBy);
    }
}