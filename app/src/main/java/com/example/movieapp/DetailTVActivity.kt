package com.example.movieapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.ActivityDetailTvBinding
import com.example.movieapp.models.TV

class DetailTVActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TvS = "extra_tvs"
    }

    private lateinit var binding: ActivityDetailTvBinding
    private lateinit var viewModel: DetailTVViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.imgItemPhoto.clipToOutline = true

        viewModel = ViewModelProvider(this).get(DetailTVViewModel::class.java)

        if (viewModel.tvDetail == null) {
            viewModel.tvDetail = intent.getParcelableExtra(EXTRA_TvS)
        }

        viewModel.tvDetail?.let { tv ->
            displayTvDetails(tv)
        }
    }

    private fun displayTvDetails(tv: TV) {
        val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
        Glide.with(this).load(IMAGE_BASE + tv.poster).into(binding.imgItemPhoto)
        binding.tvItemName.text = tv.title
        binding.tvItemDescription.text = tv.overview
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
