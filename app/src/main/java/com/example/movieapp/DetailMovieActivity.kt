package com.example.movieapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.ActivityDetailMovieBinding
import com.example.movieapp.models.Movie
import com.example.movieapp.viewmodels.DetailMovieViewModel

class DetailMovieActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIES = "extra_movies"
        const val MOVIE_DETAIL_KEY = "movie_detail_key"
    }

    private lateinit var binding: ActivityDetailMovieBinding
    private lateinit var viewModel: DetailMovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.imgItemPhoto.clipToOutline = true

        viewModel = ViewModelProvider(this).get(DetailMovieViewModel::class.java)

        if (savedInstanceState != null) {
            viewModel.movieDetail = savedInstanceState.getParcelable(MOVIE_DETAIL_KEY)
        } else {
            viewModel.movieDetail = if (android.os.Build.VERSION.SDK_INT >= 33) {
                intent.getParcelableExtra(EXTRA_MOVIES, Movie::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.getParcelableExtra(EXTRA_MOVIES)
            }
        }

        viewModel.movieDetail?.let { movie ->
            displayMovieDetails(movie)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(MOVIE_DETAIL_KEY, viewModel.movieDetail)
    }

    private fun displayMovieDetails(movie: Movie) {
        val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
        Glide.with(this).load(IMAGE_BASE + movie.poster).into(binding.imgItemPhoto)
        binding.tvItemName.text = movie.title
        binding.tvItemDescription.text = movie.overview
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
