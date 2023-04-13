package dm.sample.mova.domain.repositories

import dm.sample.mova.domain.entities.response.GenreResponse

interface GenreRepository {
    suspend fun getGenresFlow(): GenreResponse
    suspend fun getGenres(): GenreResponse
}