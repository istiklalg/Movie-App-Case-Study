package com.istiklal.movieschallenge.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.google.android.material.snackbar.Snackbar
import com.istiklal.movieschallenge.databinding.ActivityDetailBinding
import com.istiklal.movieschallenge.models.Movie
import com.istiklal.movieschallenge.utilities.dataManagement.MoviesRepository

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding

    private var movieId : Long = 0L
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val detail_intent = intent
        movieId = intent.getLongExtra("movie_id", 0L)
        if (movieId == 0L){
            Snackbar.make(view, "Couldn't get movie id !!", Snackbar.LENGTH_LONG).show()
        } else {
            getMovie(movieId)
        }

    }

    private fun getMovie(id : Long) {
        MoviesRepository.getMovie(
            id = id,
            onSuccess = ::movieDetailsFetched,
            onError = ::onError
        )
    }

    private fun movieDetailsFetched(fetchedMovie: Movie) {
        movie = fetchedMovie
        Snackbar.make(binding.root, "We got movie with id $movieId", Snackbar.LENGTH_LONG).show()
        Log.d("DetailActivity.movieDetailsFetched", "Successfully got movie with id $movieId -- ${movie.id}")
        bindMovieToVew(movie)
    }

    private fun bindMovieToVew(movie : Movie) {
        Glide.with(binding.root)
            .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
            .transform(CenterCrop())
            .into(binding.movieDetailPoster)
        binding.movieDetailRate.text = "${movie.rating.toString()} / 10"
        binding.movieDetailRelease.text = movie.releaseDate.toString()
        binding.movieDetailTitle.text = "${movie.title} (${movie.releaseDate.slice(0..3)})"
        binding.movieDetailOverview.text = movie.overview
    }

    private fun onError() {
        Snackbar.make(binding.root, "Error fetching movie with id $movieId...", Snackbar.LENGTH_LONG).show()
    }
}