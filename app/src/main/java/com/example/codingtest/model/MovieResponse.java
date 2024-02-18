package com.example.codingtest.model;

import com.example.codingtest.model.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {
    public int page;
    public List<Movie> results;
    @SerializedName("total_pages")
    public int totalPages;
    @SerializedName("total_results")
    public int totalResults;

    public int getPage() {
        return page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }
}

