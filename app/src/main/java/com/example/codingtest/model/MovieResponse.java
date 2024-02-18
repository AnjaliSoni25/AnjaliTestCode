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

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
