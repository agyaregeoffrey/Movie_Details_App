package com.dev.gka.abda.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.gka.abda.utilities.ApiStatus
import com.dev.gka.abda.utilities.Constants
import com.dev.gka.abda.model.Result
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

    // Banner image
    private val _movieOnBanner = MutableLiveData<Result>()
    val onBanner: LiveData<Result> get() = _movieOnBanner

    // Navigation
    private val _navigateToSelectedMovie = MutableLiveData<Result>()
    val navigateToSelected: LiveData<Result>
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
                            Constants.PAGES
                        )
                    )
                for (movie in popularMovies) {
                    _popular.value = movie.results
                }

                _movieOnBanner.value = popularMovies[0].results.random()
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
                            Constants.API_KEY,
                            Constants.PAGES
                        )
                    )
                for (movie in topMovies) {
                    _top.value = movie.results
                }
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _top.value = ArrayList()
                Timber.w("Failure: ${e.message}")
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
                            Constants.TIME_WINDOW_WEEK,
                            Constants.API_KEY,
                            Constants.PAGES
                        )
                    )
                _trending.value = trendingMovies[0].results
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _top.value = ArrayList()
                Timber.w("Failure: ${e.message}")
            }
        }
    }

    fun navigateToSelectedMovieDetails(property: Result) {
        _navigateToSelectedMovie.value = property
    }

    fun navigateToSelectedMovieDetailsComplete() {
        _navigateToSelectedMovie.value = null
    }
}