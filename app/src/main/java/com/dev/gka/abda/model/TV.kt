package com.dev.gka.abda.model

data class TV(
    val page: Int,
    val results: List<TvResult>,
    val total_pages: Int,
    val total_results: Int
)