package dm.sample.mova.domain.repositories

import dm.sample.mova.domain.entities.response.CreateSessionResponse
import dm.sample.mova.domain.entities.response.DeleteSessionResponse
import dm.sample.mova.domain.enums.LoginType

interface AuthRepository {

    suspend fun createSessionAsUser(userName: String, password: String): CreateSessionResponse

    suspend fun createSessionAsGuest(): CreateSessionResponse

    suspend fun logoutUser(): DeleteSessionResponse

    suspend fun savePinCode(pinCode: String)

    suspend fun deletePinCode()

    suspend fun getPinCode(): String

    suspend fun isUserLoggedIn(): Boolean

    suspend fun isOnBoardingComplete(): Boolean

    suspend fun completeOnBoarding()

    suspend fun updateBiometricStatus(isEnabled: Boolean)

    suspend fun isBiometricEnabled(): Boolean

    suspend fun setLoginType(loginType: LoginType)

    suspend fun getLoginType() : LoginType
}