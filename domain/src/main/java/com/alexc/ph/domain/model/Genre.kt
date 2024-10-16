package com.alexc.ph.domain.model

import com.alexc.ph.data.network.model.GenreResponse

data class Genre(
    val id: Int,
    val name: String
)

fun GenreResponse.toGenre() = Genre(
    id = this.id,
    name = this.name
)
