package dm.sample.data.repositories

import dm.sample.data.remote.ServerApi
import dm.sample.data.remote.dtos.response.toDomain
import dm.sample.data.repositories.base.BaseRepository
import dm.sample.mova.domain.entities.response.GenreResponse
import dm.sample.mova.domain.repositories.GenreRepository
import com.google.gson.Gson
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val serverApi: ServerApi,
    gson: Gson
) : GenreRepository, BaseRepository(gson) {

    override suspend fun getGenresFlow() = doRequest {
        serverApi.getGenre().toDomain()
    }

    override suspend fun getGenres(): GenreResponse {
        return serverApi.getGenre().toDomain()
    }
}