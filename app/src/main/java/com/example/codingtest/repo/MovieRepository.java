package com.example.codingtest.repo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.codingtest.model.MovieResponse;
import com.example.codingtest.model.Movie;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    private ApiService apiService;

    public MovieRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public LiveData<List<Movie>> getMovies(boolean includeAdult, boolean includeVideo, String language, int page, String sortBy) {
        MutableLiveData<List<Movie>> moviesLiveData = new MutableLiveData<>();
        try {
            callMovieAPI(moviesLiveData,includeAdult, includeVideo, language, page, sortBy);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("NetworkError", "Exception: " + e.getMessage());
        }
        return moviesLiveData;
    }


    public LiveData<List<Movie>> getMoreMovies(boolean includeAdult, boolean includeVideo, String language, int page, String sortBy) {
        MutableLiveData<List<Movie>> moviesLiveData = new MutableLiveData<>();
        try {
            callMovieAPI(moviesLiveData,includeAdult, includeVideo, language, page, sortBy);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("NetworkError", "Exception: " + e.getMessage());
        }        return moviesLiveData;
    }

    private void callMovieAPI(MutableLiveData<List<Movie>> moviesLiveData, boolean includeAdult, boolean includeVideo, String language, int page, String sortBy) {
        apiService.discoverMovies(includeAdult, includeVideo, language, page, sortBy)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            MovieResponse movieResponse = response.body();
                            List<Movie> movies = movieResponse != null ? movieResponse.getResults() : new ArrayList<>();
                            moviesLiveData.setValue(movies);
                        } else {
                            moviesLiveData.setValue(null); // Handle error
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        moviesLiveData.setValue(null); // Handle failure
                    }
                });

    }
}
