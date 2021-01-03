package com.example.composify.spotify.data

import java.io.Serializable

class Album(
        val id: Int,
        val genre: String = "Pop",
        val artist: String,
        val song: String,
        val descriptions: String,
        val imageId: Int,
        val swiped: Boolean = false
) : Serializable