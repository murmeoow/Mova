package dm.sample.data.local.prefsstore

import dm.sample.mova.domain.enums.LoginType
import kotlinx.coroutines.flow.Flow
import java.util.*

interface MovaDataStore {

    val getPinCode: Flow<String>

    val getBiometryEnabled: Flow<Boolean>

    val getSessionId: Flow<String>

    val isOnBoardingComplete: Flow<Boolean>

    val getAccountId: Flow<Int?>

    val getFavouriteListId: Flow<Int?>

    val getAccountNickname : Flow<String?>

    val getAccountFullname: Flow<String?>

    val getAccountEmail: Flow<String?>

    val getAccountGender: Flow<String?>

    val getAccountPhoneNumber: Flow<String?>

    val getAccountPhoneCode: Flow<String?>

    val getAccountAvatar: Flow<String?>

    val getAccountCountryCode: Flow<String?>

    val getLoginType: Flow<LoginType>

    val isDarkTheme: Flow<Boolean>

    val getLanguage: Flow<Locale>

    val getSubscriptionStatus: Flow<Boolean>

    suspend fun savePinCode(pinCode: String?)

    suspend fun saveSessionId(sessionId: String?)

    suspend fun setBiometryEnabled(inEnabled: Boolean)

    suspend fun saveAccountId(id: Int)

    suspend fun saveFavouriteListId(listId: Int)

    suspend fun clear()

    suspend fun setOnBoardingComplete(isComplete: Boolean)

    suspend fun saveAccountNickName(nickname: String)

    suspend fun saveAccountFullname(fullname: String)

    suspend fun saveAccountEmail(email: String)

    suspend fun saveAccountGender(gender: String)

    suspend fun saveAccountPhoneNumber(phoneNumber: String)

    suspend fun saveAccountPhoneCode(phoneCode: String)

    suspend fun saveAccountAvatar(path: String)

    suspend fun saveAccountCountryCode(nameCode: String)

    suspend fun setLanguage(language: Locale)

    suspend fun setDarkTheme(isDarkTheme: Boolean)

    suspend fun setLoginType(loginType: LoginType)

    suspend fun setSubscriptionStatus(hasSubscription: Boolean)
}