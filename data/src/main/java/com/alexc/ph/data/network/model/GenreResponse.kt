package com.alexc.ph.data.network.model

import com.alexc.ph.domain.model.Genre
import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
    val id: Int,
    val name: String
)

fun GenreResponse.toGenre() = Genre(
    id = this.id,
    name = this.name
)

