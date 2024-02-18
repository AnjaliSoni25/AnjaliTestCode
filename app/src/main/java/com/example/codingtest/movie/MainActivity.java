package com.example.codingtest.movie;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.codingtest.MovieType;
import com.example.codingtest.R;
import com.example.codingtest.model.Movie;
import com.example.codingtest.repo.MovieRepository;
import com.example.codingtest.repo.ApiService;
import com.example.codingtest.repo.RetrofitClient;
import com.example.codingtest.viewModel.MainActivityViewModel;
import com.example.codingtest.viewModel.MovieViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;
    private MovieAdapter adapter;
    private boolean isLoading = false;

    private String API_KEY="cc22d35a230f292eb26c1520a1f8829e";
    private  String sortType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initRetrofit();
        defaultViewSelection();

    }

    // Set up   Repository
    private void initRetrofit() {
        ApiService apiService = RetrofitClient.getApiService();
        MovieRepository movieRepository = new MovieRepository(apiService);
        initViewModel(movieRepository);
        setAdapter();
    }

    private void defaultViewSelection() {
        fetchMovies(MovieType.POPULAR);
        sortTypeSelection();
    }

    // Make the API call
    private void makeAPICall(String sortType) {
        viewModel.getMovies(false, false, "en-US", 1, sortType)
                .observe(this, movies -> adapter.setMovies(movies));

    }

    private void initViewModel(MovieRepository movieRepository) {
        viewModel = new ViewModelProvider(this, new MovieViewModelFactory(movieRepository)).get(MainActivityViewModel.class);
        binding.setViewModel(viewModel);
        binding.setActivity(this);
        binding.setLifecycleOwner(this);
    }

    private void sortTypeSelection() {
        binding.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rbPopular:
                    fetchMovies(MovieType.POPULAR);
                    break;
                case R.id.rbRating:
                    fetchMovies(MovieType.TOP_RATED);
                    break;
            }
        });
    }

    private void fetchMovies(MovieType movieType) {
        new Thread(() -> {
            try {
                List<Movie> movies = null;
                switch (movieType) {
                    case POPULAR:
                        sortType="popularity.desc";
                        makeAPICall(sortType);
                        break;
                    case TOP_RATED:
                        sortType="vote_count.desc";
                        makeAPICall(sortType);
                        break;
                    default:
                        movies = new ArrayList<>();
                        break;
                }

                List<Movie> finalMovies = movies;
                runOnUiThread(() -> adapter.setMovies(finalMovies));

            } catch (Exception e) {
                // Handle error
                e.printStackTrace();
            }
        }).start();
    }


    private void setAdapter() {
        adapter = new MovieAdapter(this);
        binding.grid.setLayoutManager(new GridLayoutManager(this, 2));
        binding.grid.setAdapter(adapter);

        addScrollListener();
    }

    private void addScrollListener() {
        binding.grid.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int firstVisibleItemPosition = ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                    // Load more movies when reaching the end
                    isLoading = true;
                    viewModel.getMoreMovies(false, false, "en-US", sortType)
                            .observe(MainActivity.this, new Observer<List<Movie>>() {
                                @Override
                                public void onChanged(List<Movie> movies) {
                                    if (movies != null) {
                                        adapter.addMovies(movies);
                                    }
                                    isLoading = false;
                                }
                            });
                }
            }
        });

    }


}