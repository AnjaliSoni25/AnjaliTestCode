package com.example.codingtest.detail;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.codingtest.model.Movie;
import com.example.codingtest.R;
import com.example.codingtest.databinding.ActivityDetailBinding;



public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        getData();
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent.hasExtra("MOVIE_EXTRA")) {
            Movie movie = intent.getParcelableExtra("MOVIE_EXTRA");
            setDetailData(movie);
        }

    }

    private void setDetailData(Movie movie) {
        Glide.with(this)
                .load(movie.getPosterPath())
                .placeholder(R.drawable.ic_launcher_background)
                .into(mBinding.ivList);

        mBinding.tvTitle.setText(movie.getTitle());
        mBinding.tvOverview.setText(movie.getOverview());
    }


}