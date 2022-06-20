package com.istiklal.movieschallenge.dal

import com.istiklal.movieschallenge.models.GetMoviesResponse
import com.istiklal.movieschallenge.models.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface Api {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "YOUR_API_KEY",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = "YOUR_API_KEY",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = "YOUR_API_KEY",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/{id}")
    fun getMovie(
        @Path(value = "id", encoded = true) id : Long,
        @Query("api_key") apiKey: String = "YOUR_API_KEY",
    ): Call<Movie>
}
