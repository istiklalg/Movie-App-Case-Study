package com.istiklal.movieschallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.istiklal.movieschallenge.databinding.ActivityMainBinding
import com.istiklal.movieschallenge.models.Movie
import com.istiklal.movieschallenge.utilities.dataManagement.MoviesRepository
import com.istiklal.movieschallenge.viewmodels.MoviesAdapter
import com.istiklal.movieschallenge.viewmodels.MoviesUpcomingAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private lateinit var popularMovies: RecyclerView
    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var popularMoviesLayoutManager: LinearLayoutManager
    private var popularMoviesPage = 1

    private lateinit var upcomingMovies: RecyclerView
    private lateinit var upcomingMoviesAdapter: MoviesUpcomingAdapter
    private lateinit var upcomingMoviesLayoutManager : LinearLayoutManager
    private var upcomingMoviesPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        popularMovies = binding.popularMovies
        popularMoviesLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        popularMovies.layoutManager = popularMoviesLayoutManager
        popularMoviesAdapter = MoviesAdapter(mutableListOf())
        popularMovies.adapter = popularMoviesAdapter

        upcomingMovies = binding.upcomingMovies
        upcomingMoviesLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        upcomingMovies.layoutManager = upcomingMoviesLayoutManager
        upcomingMoviesAdapter = MoviesUpcomingAdapter(mutableListOf())
        upcomingMovies.adapter = upcomingMoviesAdapter

        getPopularMovies()
        getUpcomingMovies()

    }

    private fun onPopularMoviesFetched(movies: List<Movie>) {
        popularMoviesAdapter.appendMovies(movies)
        attachPopularMoviesOnScrollListener()
        Snackbar.make(binding.root, "Successfully fetched now playing movies", Snackbar.LENGTH_LONG).show()
    }

    private fun getPopularMovies() {
        MoviesRepository.getPopularMovies(
            popularMoviesPage,
            onSuccess = ::onPopularMoviesFetched,
            onError = ::onError
        )
    }

    private fun attachPopularMoviesOnScrollListener() {
        popularMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = popularMoviesLayoutManager.itemCount
                val visibleItemCount = popularMoviesLayoutManager.childCount
                val firstVisibleItem = popularMoviesLayoutManager.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    popularMovies.removeOnScrollListener(this)
                    popularMoviesPage++
                    getPopularMovies()
                }
            }
        })
    }

    private fun onError() {
        Snackbar.make(binding.root, "Error fetching now playing movies", Snackbar.LENGTH_LONG).show()
    }

    private fun onUpcomingMoviesFetched(movies : List<Movie>) {
        upcomingMoviesAdapter.appendMovies(movies)
        attachUpcomingMoviesOnScrollListener()
        Snackbar.make(binding.root, "Successfully fetched upcoming movies", Snackbar.LENGTH_LONG).show()
    }

    private fun getUpcomingMovies() {
        MoviesRepository.getUpcomingMovies(
            upcomingMoviesPage,
            onSuccess = ::onUpcomingMoviesFetched,
            onError = ::onUpcomingError
        )
    }

    private fun attachUpcomingMoviesOnScrollListener() {
        upcomingMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = upcomingMoviesLayoutManager.itemCount
                val visibleItemCount = upcomingMoviesLayoutManager.childCount
                val firstVisibleItem = upcomingMoviesLayoutManager.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    upcomingMovies.removeOnScrollListener(this)
                    upcomingMoviesPage++
                    getUpcomingMovies()
                }
            }
        })
    }

    private fun onUpcomingError() {
        Snackbar.make(binding.root, "Error fetching upcoming movies", Snackbar.LENGTH_LONG).show()
    }
}