package com.dev.gka.abda.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dev.gka.abda.model.Result

class DetailsViewModelFactory(private val result: Result) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(result) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}