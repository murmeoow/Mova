package dm.sample.data.repositories

import dm.sample.data.local.prefsstore.MovaDataStore
import dm.sample.data.remote.ServerApi
import dm.sample.data.remote.dtos.request.toData
import dm.sample.data.remote.dtos.response.toDomain
import dm.sample.data.repositories.base.BaseRepository
import dm.sample.mova.domain.entities.request.AddRemoveToMyListRequest
import dm.sample.mova.domain.entities.request.CreateMyListRequest
import dm.sample.mova.domain.entities.response.CheckIsMovieInMyListResponse
import dm.sample.mova.domain.repositories.MyListRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class MyListRepositoryImpl @Inject constructor(
    private val serverApi: ServerApi,
    private val dataStore: MovaDataStore,
    gson: Gson
) : MyListRepository, BaseRepository(gson) {

    override suspend fun getMyListDetails() = doRequest {
        val listId = dataStore.getFavouriteListId.first()
        if (listId !=  null) {
            val response = serverApi.getMyListDetails(listId)
            response.toDomain().items
        } else {
            null
        }
    }

    override suspend fun checkIsMovieInFavourite(movieId: Int) = doRequest {
        val listId = dataStore.getFavouriteListId.first()
        if (listId != null) {
            serverApi.checkIsMovieInFavourite(listId, movieId).toDomain()
        } else {
            CheckIsMovieInMyListResponse(isChecked = false)
        }
    }

    override suspend fun addMovieToMyList(movieId: Int) = doRequest {
        val listId = dataStore.getFavouriteListId.first()
        val sessionId = dataStore.getSessionId.first()
        if (listId != null) {
            val response = serverApi.addMovieToMyList(
                listId = listId,
                sessionId = sessionId,
                request = AddRemoveToMyListRequest(movieId).toData()
            )
            response.statusCode == 12
        } else {
            val response = serverApi.createMyList(
                sessionId = sessionId,
                request = CreateMyListRequest(
                    name = "Favourites",
                    description = "My favourites movies and TV shows").toData()
            )
            dataStore.saveFavouriteListId(response.listId)
            if (response.success) {
                val listId2 = dataStore.getFavouriteListId.first()
                val response2 = serverApi.addMovieToMyList(
                    listId = listId2!!,
                    sessionId = sessionId,
                    request = AddRemoveToMyListRequest(movieId).toData()
                )
                response2.statusCode == 12
            } else {
                false
            }
        }
    }

    override suspend fun removeMovieFromMyList(movieId: Long) = doRequest {
        val listId = dataStore.getFavouriteListId.first()
        val sessionId = dataStore.getSessionId.first()
        val response = serverApi.removeMovieFromMyList(
            listId = listId!!,
            sessionId = sessionId,
            request = AddRemoveToMyListRequest(mediaId = movieId.toInt()).toData()
        )
       response.statusCode == 13
    }
}