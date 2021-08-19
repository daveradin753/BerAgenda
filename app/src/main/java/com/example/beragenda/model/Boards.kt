package com.example.beragenda.model

data class Boards(
    val project_name: String,
    val board_id: String,
    val user_id: List<String>? = null,
    val board_imageURL: String = "",
    val board_hex_color: String = "#9EF5CF")
