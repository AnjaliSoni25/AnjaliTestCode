package com.example.codingtest.repo;
import com.example.codingtest.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiService {
    @GET("3/discover/movie")
    Call<MovieResponse> discoverMovies(
            @Query("include_adult") boolean includeAdult,
            @Query("include_video") boolean includeVideo,
            @Query("language") String language,
            @Query("page") int page,
            @Query("sort_by") String sortBy);
}