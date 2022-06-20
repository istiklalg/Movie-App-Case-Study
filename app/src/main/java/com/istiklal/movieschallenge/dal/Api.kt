package com.istiklal.movieschallenge.dal

import com.istiklal.movieschallenge.models.GetMoviesResponse
import com.istiklal.movieschallenge.models.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

/*
* @author: istiklal
* API KEY : f725c592d7aa4844e0a9e95a443da7a4
* API request sample ->https://api.themoviedb.org/3/movie/550?api_key=f725c592d7aa4844e0a9e95a443da7a4
*
* API Read Access Token : eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmNzI1YzU5MmQ3YWE0ODQ0ZTBhOWU5NWE0NDNkYTdhNCIsInN1YiI6IjYyYTZkYWQ0N2Y2YzhkNThlYWYzODZhNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.tdFgT4OeukQnJwOi9lkRYXEagdZemau9LWNo529p3jo
*
* */

// apiAccessData = ApiAccessData(mainUrl = "https://api.themoviedb.org/3", accessKey = "f725c592d7aa4844e0a9e95a443da7a4")

interface Api {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "f725c592d7aa4844e0a9e95a443da7a4",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = "f725c592d7aa4844e0a9e95a443da7a4",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = "f725c592d7aa4844e0a9e95a443da7a4",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/{id}")
    fun getMovie(
        @Path(value = "id", encoded = true) id : Long,
        @Query("api_key") apiKey: String = "f725c592d7aa4844e0a9e95a443da7a4",
    ): Call<Movie>
}