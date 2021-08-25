package com.dev.gka.abda.network

import com.dev.gka.abda.model.Movie
import com.dev.gka.abda.model.TV
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


// Full Url -> https://api.themoviedb.org/3/movie/popular?api_key=c95054e473cdf9edeb1314ab7af98477

private const val BASE_URL = "https://api.themoviedb.org/3/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .client(
        OkHttpClient().newBuilder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()
    )
    .baseUrl(BASE_URL)
    .build()


interface MovieApiService {
    
    @GET("movie/{popular}")
    suspend fun getPopularMovies(
        @Path("popular") popular: String,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
    ): Movie

    @GET("movie/{top_rated}")
    suspend fun getTopRatedMovies(
        @Path("top_rated") topRated: String,
        @Query("api_key") apiKey: String
    ): Movie

    @GET("trending/{media_type}/{time_window}")
    suspend fun getTrendingMovies(
        @Path("media_type") mediaType: String,
        @Path("time_window") timeWindow: String,
        @Query("api_key") apiKey: String
    ): Movie

    // TV endpoints
    @GET("tv/{airing_today}")
    suspend fun airingTodaySeries(
        @Path("airing_today") airingToday: String,
        @Query("api_key") apiKey: String
    ): TV

    @GET("tv/{latest}")
    suspend fun getLatestSeries(
        @Path("latest") latest: String,
        @Query("api_key") apiKey: String
    ): TV

    @GET("tv/{popular}")
    suspend fun getPopularSeries(
        @Path("popular") popular: String,
        @Query("api_key") apiKey: String
    ): TV

    @GET("tv/{top_rated}")
    suspend fun getTopRatedSeries(
        @Path("top_rated") topRated: String,
        @Query("api_key") apiKey: String
    ): TV
}

object MyApi {
    val retrofitService: MovieApiService by lazy {
        retrofit.create(MovieApiService::class.java)
    }
}