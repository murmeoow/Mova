package dm.sample.data.local.db.entites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dm.sample.mova.domain.entities.MovieChange
import java.time.LocalDate

@Entity(tableName = "movie_changes")
data class MovieChangeEntity(
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = "adult")
    val adult: Boolean?,
    @ColumnInfo(name = "created_at", defaultValue = "CURRENT_TIMESTAMP")
    val createdAt: LocalDate,
)


fun MovieChange.toEntity() = MovieChangeEntity(
    id = id,
    adult = adult,
    createdAt = LocalDate.now(),
)

fun MovieChangeEntity.toDomain() = MovieChange(
    id = id,
    adult = adult,
    movie = null,
)