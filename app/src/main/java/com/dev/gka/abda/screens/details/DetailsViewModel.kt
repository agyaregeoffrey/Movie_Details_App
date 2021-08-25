package com.dev.gka.abda.screens.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.gka.abda.model.MovieResult

class DetailsViewModel(movieResult: MovieResult) : ViewModel() {
    private val _selectedMovie = MutableLiveData<MovieResult>()
    val selectedMovie: LiveData<MovieResult>
        get() = _selectedMovie

    init {
        _selectedMovie.value = movieResult
    }

}