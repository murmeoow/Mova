package dm.sample.data.repositories

import DeleteSessionRequestDto
import dm.sample.data.local.prefsstore.MovaDataStore
import dm.sample.data.remote.ServerApi
import dm.sample.data.remote.dtos.request.CreateSessionRequestDto
import dm.sample.data.remote.dtos.request.ValidateUserRequestDto
import dm.sample.data.remote.dtos.response.CreateSessionResponseDto
import dm.sample.data.remote.dtos.response.DeleteSessionResponseDto
import dm.sample.data.remote.dtos.response.toDomain
import dm.sample.data.repositories.base.BaseRepository
import dm.sample.mova.domain.entities.response.CreateSessionResponse
import dm.sample.mova.domain.enums.LoginType
import dm.sample.mova.domain.repositories.AuthRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataStore: MovaDataStore,
    private val serverApi: ServerApi,
    gson: Gson,
) : AuthRepository, BaseRepository(gson) {

    override suspend fun createSessionAsUser(userName: String, password: String) = doRequest {
        val tokenResponse = serverApi.createRequestToken()
        val validateUserBody = ValidateUserRequestDto(userName, password, tokenResponse.requestToken)
        val validateUserResponse = serverApi.validateUser(validateUserBody)
        if (validateUserResponse.success) {
            val createSessionRequest = CreateSessionRequestDto(validateUserResponse.requestToken)
            val session = serverApi.createSession(createSessionRequest).toDomain()
            dataStore.saveSessionId(session.sessionId)
            session
        } else {
            CreateSessionResponseDto(false, "").toDomain()
        }
    }

    override suspend fun createSessionAsGuest(): CreateSessionResponse  = doRequest{
        val session = serverApi.createSessionAsGuest()
        dataStore.saveSessionId(session.guestSessionId)
        CreateSessionResponse(session.success, session.guestSessionId)
    }

    override suspend fun logoutUser() = doRequest {
        when(getLoginType()) {
            LoginType.STANDARD_LOGIN -> {
                val sessionId = dataStore.getSessionId.first()
                val response = serverApi.deleteSession(DeleteSessionRequestDto(sessionId))
                if (response.success) {
                    dataStore.clear()
                    response.toDomain()
                } else {
                    DeleteSessionResponseDto(false).toDomain()
                }
            }
            LoginType.GUEST_LOGIN -> {
                dataStore.clear()
                DeleteSessionResponseDto(true).toDomain()
            }
        }
    }

    override suspend fun savePinCode(pinCode: String) {
        dataStore.savePinCode(pinCode)
    }

    override suspend fun deletePinCode() {
        dataStore.savePinCode(null)
    }

    override suspend fun getPinCode(): String {
        return dataStore.getPinCode.first()
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return dataStore.getSessionId.first().isNotEmpty()
    }

    override suspend fun isOnBoardingComplete(): Boolean {
        return dataStore.isOnBoardingComplete.first()
    }

    override suspend fun completeOnBoarding() {
        dataStore.setOnBoardingComplete(true)
    }

    override suspend fun updateBiometricStatus(isEnabled: Boolean) {
        dataStore.setBiometryEnabled(isEnabled)
    }

    override suspend fun isBiometricEnabled(): Boolean {
        return dataStore.getBiometryEnabled.first()
    }

    override suspend fun setLoginType(loginType: LoginType) {
        dataStore.setLoginType(loginType)
    }

    override suspend fun getLoginType(): LoginType {
        return dataStore.getLoginType.first()
    }

}