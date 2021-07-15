package com.example.beragenda.model

data class Users(
    val uid: String,
    val username: String,
    val email: String,
    val profilePicURL: String = ""
)