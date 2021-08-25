package com.dev.gka.abda.model

import android.net.Uri

data class User(
    val name: String?,
    val email: String?,
    val imageLink: Uri?,
    val phone: String?
)