package com.istiklal.movieschallenge.utilities.dataManagement

import android.util.Log
import com.google.gson.annotations.SerializedName
import com.istiklal.movieschallenge.dal.Api
import com.istiklal.movieschallenge.models.GetMoviesResponse
import com.istiklal.movieschallenge.models.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.KFunction0
import kotlin.reflect.KFunction1

object MoviesRepository {

    private val api: Api
    private const val TAG = "dataManagement.MoviesRepository"

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(Api::class.java)
    }

    fun getPopularMovies(
        page: Int = 1,
        onSuccess: (List<Movie>) -> Unit,
        onError: () -> Unit
        ) {
            api.getPopularMovies(page = page)
                .enqueue(object : Callback<GetMoviesResponse> {
                    override fun onResponse(
                        call: Call<GetMoviesResponse>,
                        response: Response<GetMoviesResponse>
                    ) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()

                            if (responseBody != null) {
                                onSuccess.invoke(responseBody.movies)
                                Log.d("$TAG Popular Movies", "Movies: ${responseBody.movies}")
                            } else {
                                onError.invoke()
                                Log.d("$TAG Popular Movies", "Failed to get response")
                            }
                        } else {
                            onError.invoke()
                        }
                    }

                    override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                        onError.invoke()
                        Log.e("$TAG Popular Movies", "onFailure", t)
                    }
                })
    }

    fun getUpcomingMovies(
        page: Int = 1,
        onSuccess: (List<Movie>) -> Unit,
        onError: () -> Unit
        ) {
        api.getUpcomingMovies(page = page)
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                            Log.d("$TAG Upcoming Movies", "Movies: ${responseBody.movies}")
                        } else {
                            onError.invoke()
                            Log.d("$TAG Upcoming Movies", "Failed to get response")
                        }
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    Log.e("$TAG Upcoming Movies", "onFailure", t)
                }
            })
    }

    fun  getMovie(
        id: Long,
//        onSuccess: KFunction1<Movie, Unit>,
        onSuccess: (Movie) -> Unit,
        onError: () -> Unit
        ) {
            api.getMovie(id=id)
                .enqueue(object : Callback<Movie> {
                    override fun onResponse(
                        call: Call<Movie>,
                        response: Response<Movie>) {
                            if (response.isSuccessful) {
                                val responseBody = response.body()

                                if (responseBody != null) {
                                    onSuccess.invoke(responseBody)
                                    Log.d("$TAG Movie Detail", "Movie with id ($id) : $responseBody")
//                                    id: Long,
//                                    title: String,
//                                    overview: String,
//                                    posterPath: String,
//                                    backdropPath: String,
//                                    rating: Float,
//                                    releaseDate: String
                                } else {
                                    onError.invoke()
                                    Log.d("$TAG Movie", "Failed to get movie with id : $id")
                                }
                        }
                    }

                    override fun onFailure(call: Call<Movie>, t: Throwable) {
                        Log.e("$TAG Movie Detail", "onFailure", t)
                    }
            })
        }

}