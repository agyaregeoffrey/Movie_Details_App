package com.dev.gka.abda.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.gka.abda.Constants
import com.dev.gka.abda.model.Result
import com.dev.gka.abda.model.Movie
import com.dev.gka.abda.network.MovieApi
import kotlinx.coroutines.launch
import timber.log.Timber

enum class MovieApiStatus { LOADING, DONE, ERROR }

class HomeViewModel : ViewModel() {
    // Status of Api
    private val _status = MutableLiveData<MovieApiStatus>()
    val status: LiveData<MovieApiStatus>
        get() = _status

    // Popular Movies
    private val _popular = MutableLiveData<List<Result>>()
    val popular: LiveData<List<Result>>
        get() = _popular

    // Top Rated Movies
    private val _top = MutableLiveData<List<Result>>()
    val top: LiveData<List<Result>>
        get() = _top

    // Trending Movies
    private val _trending = MutableLiveData<List<Result>>()
    val trending: LiveData<List<Result>>
        get() = _trending

    // Navigation
    private val _navigateToSelectedMovie = MutableLiveData<Result>()
    val navigateToSelectedMovie: LiveData<Result>
        get() = _navigateToSelectedMovie

    init {
        retrievePopularMovies()
        retrieveTopRatedMovies()
        retrieveTrendingMovies()
    }

    private fun retrievePopularMovies() {
        var popularMovies: MutableList<Movie>
        viewModelScope.launch {
            _status.value = MovieApiStatus.LOADING
            try {
                popularMovies =
                    mutableListOf(
                        MovieApi.retrofitService.getPopularMovies(
                            Constants.POPULAR_PATH,
                            Constants.API_KEY
                        )
                    )
                _popular.value = popularMovies[0].results
                Timber.d("Poster Path ${popularMovies[0].results[0].poster_path}")
                _status.value = MovieApiStatus.DONE
            } catch (e: Exception) {
                _status.value = MovieApiStatus.ERROR
                Timber.w("Failure: ${e.message}")
            }
        }
    }

    private fun retrieveTopRatedMovies() {
        var topMovies: MutableList<Movie>
        viewModelScope.launch {
            _status.value = MovieApiStatus.LOADING
            try {
                topMovies =
                    mutableListOf(
                        MovieApi.retrofitService.getTopRatedMovies(
                            Constants.TOP_RATED_PATH,
                            Constants.API_KEY
                        )
                    )
                _top.value = topMovies[0].results
                _status.value = MovieApiStatus.DONE
            } catch (e: Exception) {
                _status.value = MovieApiStatus.ERROR
                _top.value = ArrayList()
            }
        }
    }

    private fun retrieveTrendingMovies() {
        var trendingMovies: MutableList<Movie>
        viewModelScope.launch {
            _status.value = MovieApiStatus.LOADING
            try {
                trendingMovies =
                    mutableListOf(
                        MovieApi.retrofitService.getTrendingMovies(
                            Constants.MEDIA_TYPE_MOVIE,
                            Constants.TIME_WINDOW_WEEK, Constants.API_KEY
                        )
                    )
                _trending.value = trendingMovies[0].results
                _status.value = MovieApiStatus.DONE
            } catch (e: Exception) {
                Timber.w("Failure: ${e.message}")
                _status.value = MovieApiStatus.ERROR
                _top.value = ArrayList()
            }
        }
    }

    fun displayMovieDetails(movieProperty: Result) {
        _navigateToSelectedMovie.value = movieProperty
    }

    fun displayMovieDetailsComplete() {
        _navigateToSelectedMovie.value = null
    }
}