package dm.sample.data.local.db.entites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dm.sample.mova.domain.entities.Movie
import java.time.LocalDate

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = "adult")
    val adult: Boolean,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String?,
    @ColumnInfo(name = "original_language")
    val originalLanguage: String,
    @ColumnInfo(name = "original_title")
    val originalTitle: String,
    @ColumnInfo(name = "overview")
    val overview: String,
    @ColumnInfo(name = "popularity")
    val popularity: Float,
    @ColumnInfo(name = "poster_path")
    val posterPath: String?,
    @ColumnInfo(name = "release_date")
    val releaseDate: String?,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "video")
    val video: Boolean,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Float,
    @ColumnInfo(name = "vote_count")
    val voteCount: Float,
)

fun Movie.toEntity() = MovieEntity(
    id = id,
    adult = adult,
    backdropPath = backdropPath,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate.toString(),
    title = title,
    video = video,
    voteCount = voteCount,
    voteAverage = voteOverage,
)

fun MovieEntity.toDomain() = Movie(
    id = id,
    adult = adult,
    backdropPath = backdropPath,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = LocalDate.parse(releaseDate),
    title = title,
    video = video,
    voteCount = voteCount,
    voteOverage = voteAverage,
    genreIds = listOf() // TODO
)
