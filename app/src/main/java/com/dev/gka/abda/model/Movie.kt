package com.dev.gka.abda.model

data class Movie(
    val page: Int,
    val movieResults: List<MovieResult>,
    val total_pages: Int,
    val total_results: Int
)