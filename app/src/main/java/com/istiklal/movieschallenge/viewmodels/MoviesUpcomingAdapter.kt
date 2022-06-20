package com.istiklal.movieschallenge.viewmodels

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.istiklal.movieschallenge.R
import com.istiklal.movieschallenge.models.Movie
import com.istiklal.movieschallenge.views.DetailActivity

class MoviesUpcomingAdapter (
        private var movies: MutableList<Movie>
    ) : RecyclerView.Adapter<MoviesUpcomingAdapter.MovieUpcomingViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieUpcomingViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_upcoming, parent, false)
        return MovieUpcomingViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieUpcomingViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun updateMovies(movies: MutableList<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    fun appendMovies(movies: List<Movie>) {
        this.movies.addAll(movies)
        notifyItemRangeInserted(this.movies.size, movies.size-1)
    }

    inner class MovieUpcomingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val context : Context = itemView.context
        private val poster: ImageView = itemView.findViewById(R.id.item_upcoming_movie_poster)
        private val title : TextView = itemView.findViewById(R.id.item_upcoming_movie_title)
        private val topic : TextView = itemView.findViewById(R.id.item_upcoming_movie_overview)
        private val release : TextView = itemView.findViewById(R.id.item_upcoming_movie_release_date)
        private val verticalLinear : LinearLayout = itemView.findViewById(R.id.vertical_linear_layout)
        private val itemDetailAction : ImageView = itemView.findViewById(R.id.item_detail_action)

        fun bind(movie: Movie) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
                .transform(CenterCrop())
                .into(poster)
            title.text = "${movie.title} (${movie.releaseDate.slice(0..3)})"
            topic.text = movie.overview
            release.text = movie.releaseDate
            verticalLinear.setOnClickListener {
                val detailIntent = Intent(context, DetailActivity::class.java)
                detailIntent.putExtra("movie_id", movie.id)
                context.startActivity(detailIntent)
            }
        }
    }

}
