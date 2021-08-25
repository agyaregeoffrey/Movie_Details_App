package com.dev.gka.abda.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.gka.abda.utilities.ApiStatus
import com.dev.gka.abda.utilities.Constants
import com.dev.gka.abda.model.MovieResult
import com.dev.gka.abda.model.Movie
import com.dev.gka.abda.network.MyApi
import kotlinx.coroutines.launch
import timber.log.Timber



class HomeViewModel : ViewModel() {
    // Status of Api
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    // Popular Movies
    private val _popular = MutableLiveData<List<MovieResult>>()
    val popular: LiveData<List<MovieResult>>
        get() = _popular

    // Top Rated Movies
    private val _top = MutableLiveData<List<MovieResult>>()
    val top: LiveData<List<MovieResult>>
        get() = _top

    // Trending Movies
    private val _trending = MutableLiveData<List<MovieResult>>()
    val trending: LiveData<List<MovieResult>>
        get() = _trending

    // Banner image
    private val _movieOnBanner = MutableLiveData<MovieResult>()
    val movieOnBanner: LiveData<MovieResult> get() = _movieOnBanner

    // Navigation
    private val _navigateToSelectedMovie = MutableLiveData<MovieResult>()
    val navigateToSelectedMovie: LiveData<MovieResult>
        get() = _navigateToSelectedMovie

    init {
        retrievePopularMovies()
        retrieveTopRatedMovies()
        retrieveTrendingMovies()
    }

    private fun retrievePopularMovies() {
        var popularMovies: MutableList<Movie>
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                popularMovies =
                    mutableListOf(
                        MyApi.retrofitService.getPopularMovies(
                            Constants.POPULAR_PATH,
                            Constants.API_KEY,
                            Constants.PAGE
                        )
                    )
                for (movie in popularMovies){
                    _popular.value = movie.movieResults
                }

                _movieOnBanner.value = popularMovies[0].movieResults.random()
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                Timber.w("Failure: ${e.message}")
            }
        }
    }

    private fun retrieveTopRatedMovies() {
        var topMovies: MutableList<Movie>
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                topMovies =
                    mutableListOf(
                        MyApi.retrofitService.getTopRatedMovies(
                            Constants.TOP_RATED_PATH,
                            Constants.API_KEY
                        )
                    )
                for (movie in topMovies) {
                    _top.value = movie.movieResults
                }
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _top.value = ArrayList()
            }
        }
    }

    private fun retrieveTrendingMovies() {
        var trendingMovies: MutableList<Movie>
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                trendingMovies =
                    mutableListOf(
                        MyApi.retrofitService.getTrendingMovies(
                            Constants.MEDIA_TYPE_MOVIE,
                            Constants.TIME_WINDOW_WEEK, Constants.API_KEY
                        )
                    )
                _trending.value = trendingMovies[0].movieResults
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                Timber.w("Failure: ${e.message}")
                _status.value = ApiStatus.ERROR
                _top.value = ArrayList()
            }
        }
    }

    fun navigateToSelectedMovieDetails(movieProperty: MovieResult) {
        _navigateToSelectedMovie.value = movieProperty
    }

    fun navigateToSelectedMovieDetailsComplete() {
        _navigateToSelectedMovie.value = null
    }
}