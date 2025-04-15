package com.alexc.ph.domain.model

data class TvSeries(
    override val id: Int,
    override val title: String,
    override val posterPath: String = "",
    override val backdropPath: String = "",
    override val contentType: ContentType = ContentType.TV,
    override val genres: List<Genre> = emptyList(),
    val overview: String = "",
    val popularity: Double = .0,
    val adult: Boolean = false,
    val firstAirDate: String = "",
    val originCountry: List<String> = emptyList(),
    val originalLanguage: String = "",
    val originalName: String = "",
    val voteAverage: Double = .0,
    val voteCount: Int = 0,
    val seasons: List<Season> = emptyList()
): BaseContent()

data class Season(
    val id: Int,
    val name: String ,
    val overview: String = "",
    val airDate: String = "",
    val episodeCount: Int = 0,
    val posterPath: String = "",
    val seasonNumber: Int = 0,
    val voteAverage: Double = .0
)



