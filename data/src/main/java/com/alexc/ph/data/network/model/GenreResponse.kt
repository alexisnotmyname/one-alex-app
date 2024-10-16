package com.alexc.ph.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
    val id: Int,
    val name: String
)

