package com.alexc.ph.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class MediaTypeResponse {
    @SerialName("tv")
    TV,

    @SerialName("movie")
    MOVIE,

    @SerialName("person")
    PERSON
}