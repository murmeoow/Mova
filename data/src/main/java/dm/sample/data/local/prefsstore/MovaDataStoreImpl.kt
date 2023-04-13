package dm.sample.data.local.prefsstore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dm.sample.mova.data.BuildConfig
import dm.sample.data.local.prefsstore.MovaDataStoreImpl.MovaKeys.PREF_ACCOUNT_AVATAR_PATH
import dm.sample.data.local.prefsstore.MovaDataStoreImpl.MovaKeys.PREF_ACCOUNT_COUNTRY
import dm.sample.data.local.prefsstore.MovaDataStoreImpl.MovaKeys.PREF_ACCOUNT_EMAIL
import dm.sample.data.local.prefsstore.MovaDataStoreImpl.MovaKeys.PREF_ACCOUNT_FULLNAME
import dm.sample.data.local.prefsstore.MovaDataStoreImpl.MovaKeys.PREF_ACCOUNT_GENDER
import dm.sample.data.local.prefsstore.MovaDataStoreImpl.MovaKeys.PREF_ACCOUNT_ID
import dm.sample.data.local.prefsstore.MovaDataStoreImpl.MovaKeys.PREF_ACCOUNT_NICKNAME
import dm.sample.data.local.prefsstore.MovaDataStoreImpl.MovaKeys.PREF_ACCOUNT_PHONE_CODE
import dm.sample.data.local.prefsstore.MovaDataStoreImpl.MovaKeys.PREF_ACCOUNT_PHONE_NUMBER
import dm.sample.data.local.prefsstore.MovaDataStoreImpl.MovaKeys.PREF_FAVOURITE_LIST_ID
import dm.sample.data.local.prefsstore.MovaDataStoreImpl.MovaKeys.PREF_IS_BIOMETRY_ENABLED
import dm.sample.data.local.prefsstore.MovaDataStoreImpl.MovaKeys.PREF_LANGUAGE_COUNTRY
import dm.sample.data.local.prefsstore.MovaDataStoreImpl.MovaKeys.PREF_LANGUAGE_NAME
import dm.sample.data.local.prefsstore.MovaDataStoreImpl.MovaKeys.PREF_LOGIN_TYPE
import dm.sample.data.local.prefsstore.MovaDataStoreImpl.MovaKeys.PREF_ONBOARDING_COMPLETE
import dm.sample.data.local.prefsstore.MovaDataStoreImpl.MovaKeys.PREF_PIN_CODE
import dm.sample.data.local.prefsstore.MovaDataStoreImpl.MovaKeys.PREF_SESSION_ID
import dm.sample.data.local.prefsstore.MovaDataStoreImpl.MovaKeys.PREF_SUBSCRIPTION_STATUS
import dm.sample.data.local.prefsstore.MovaDataStoreImpl.MovaKeys.PREF_THEME_IS_DARK
import dm.sample.mova.domain.enums.LoginType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

private const val MOVA_DATA_STORE = "mova_data_store"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = MOVA_DATA_STORE)

class MovaDataStoreImpl @Inject constructor(@ApplicationContext private val context: Context) :
    MovaDataStore {

    override val getPinCode: Flow<String>
        get() = context.dataStore.data.map { preferences ->
            preferences[PREF_PIN_CODE] ?: ""
        }

    override val getBiometryEnabled: Flow<Boolean>
        get() = context.dataStore.data.map { preferences ->
            preferences[PREF_IS_BIOMETRY_ENABLED] ?: false
        }

    override val getSessionId: Flow<String>
        get() = context.dataStore.data.map { preferences ->
            preferences[PREF_SESSION_ID] ?: ""
        }

    override val isOnBoardingComplete: Flow<Boolean>
        get() = context.dataStore.data.map { preferences ->
            preferences[PREF_ONBOARDING_COMPLETE] ?: false
        }

    override val getAccountNickname: Flow<String?>
        get() = context.dataStore.data.map { preferences ->
            preferences[PREF_ACCOUNT_NICKNAME]
        }

    override val getAccountFullname: Flow<String?>
        get() = context.dataStore.data.map { preferences ->
            preferences[PREF_ACCOUNT_FULLNAME]
        }

    override val getAccountEmail: Flow<String?>
        get() = context.dataStore.data.map { preferences ->
            preferences[PREF_ACCOUNT_EMAIL]
        }

    override val getAccountGender: Flow<String?>
        get() = context.dataStore.data.map { preferences ->
            preferences[PREF_ACCOUNT_GENDER]
        }

    override val getAccountPhoneNumber: Flow<String?>
        get() = context.dataStore.data.map { preferences ->
            preferences[PREF_ACCOUNT_PHONE_NUMBER]
        }

    override val getAccountPhoneCode: Flow<String?>
        get() = context.dataStore.data.map { preferences ->
            preferences[PREF_ACCOUNT_PHONE_CODE]
        }

    override val getAccountAvatar: Flow<String?>
        get() = context.dataStore.data.map { preferences ->
            preferences[PREF_ACCOUNT_AVATAR_PATH]
        }

    override val getAccountCountryCode: Flow<String?>
        get() = context.dataStore.data.map { preferences ->
            preferences[PREF_ACCOUNT_COUNTRY]
        }

    override val getLoginType: Flow<LoginType>
        get() = context.dataStore.data.map { preference ->
            val ordinal = preference[PREF_LOGIN_TYPE] ?: LoginType.STANDARD_LOGIN.ordinal
            LoginType.fromOrdinal(ordinal)
        }

    override val isDarkTheme: Flow<Boolean>
        get() = context.dataStore.data.map { preferences ->
            preferences[PREF_THEME_IS_DARK] ?: false
        }

    override val getSubscriptionStatus: Flow<Boolean>
        get() = context.dataStore.data.map { preferences ->
            preferences[PREF_SUBSCRIPTION_STATUS] ?: false
        }

    override val getAccountId: Flow<Int?>
        get() = context.dataStore.data.map { preferences ->
            preferences[PREF_ACCOUNT_ID]
        }

    override val getFavouriteListId: Flow<Int?>
        get() = context.dataStore.data.map { preferences ->
            preferences[PREF_FAVOURITE_LIST_ID]
        }

    override val getLanguage: Flow<Locale>
        get() = context.dataStore.data.map { preferences ->
            val name = preferences[PREF_LANGUAGE_NAME]
            val country = preferences[PREF_LANGUAGE_COUNTRY]
            if (name != null && country != null) Locale(name, country)
             else Locale.getDefault()
        }


    override suspend fun setOnBoardingComplete(isComplete: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PREF_ONBOARDING_COMPLETE] = isComplete
        }
    }

    override suspend fun savePinCode(pinCode: String?) {
        context.dataStore.edit { preferences ->
            preferences[PREF_PIN_CODE] = pinCode ?: ""
        }
    }

    override suspend fun saveSessionId(sessionId: String?) {
        context.dataStore.edit { preferences ->
            preferences[PREF_SESSION_ID] = sessionId ?: ""
        }
    }

    override suspend fun setBiometryEnabled(inEnabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PREF_IS_BIOMETRY_ENABLED] = inEnabled
        }
    }

    override suspend fun saveAccountNickName(nickname: String) {
        context.dataStore.edit { preferences ->
            preferences[PREF_ACCOUNT_NICKNAME] = nickname
        }
    }

    override suspend fun saveAccountFullname(fullname: String) {
        context.dataStore.edit { preferences ->
            preferences[PREF_ACCOUNT_FULLNAME] = fullname
        }
    }

    override suspend fun saveAccountEmail(email: String) {
        context.dataStore.edit { preferences ->
            preferences[PREF_ACCOUNT_EMAIL] = email
        }
    }

    override suspend fun saveAccountGender(gender: String) {
        context.dataStore.edit { preferences ->
            preferences[PREF_ACCOUNT_GENDER] = gender
        }
    }

    override suspend fun saveAccountPhoneNumber(phoneNumber: String) {
        context.dataStore.edit { preferences ->
            preferences[PREF_ACCOUNT_PHONE_NUMBER] = phoneNumber
        }
    }

    override suspend fun saveAccountPhoneCode(phoneCode: String) {
        context.dataStore.edit { preferences ->
            preferences[PREF_ACCOUNT_PHONE_CODE] = phoneCode
        }
    }

    override suspend fun saveAccountAvatar(path: String) {
        context.dataStore.edit { preferences ->
            preferences[PREF_ACCOUNT_AVATAR_PATH] = path
        }
    }

    override suspend fun setDarkTheme(isDarkTheme: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PREF_THEME_IS_DARK] = isDarkTheme
        }
    }

    override suspend fun saveAccountId(id: Int) {
        context.dataStore.edit { preferences ->
            preferences[PREF_ACCOUNT_ID] = id
        }
    }

    override suspend fun saveAccountCountryCode(nameCode: String) {
        context.dataStore.edit { preferences ->
            preferences[PREF_ACCOUNT_COUNTRY] = nameCode
        }
    }

    override suspend fun saveFavouriteListId(listId: Int) {
        context.dataStore.edit { preferences ->
            preferences[PREF_FAVOURITE_LIST_ID] = listId
        }
    }

    override suspend fun setLoginType(loginType: LoginType) {
        context.dataStore.edit { preferences ->
            preferences[PREF_LOGIN_TYPE] = loginType.ordinal
        }
    }

    override suspend fun setLanguage(language: Locale) {
        context.dataStore.edit { preferences ->
            preferences[PREF_LANGUAGE_NAME] = language.language
            preferences[PREF_LANGUAGE_COUNTRY] = language.country
        }
    }

    override suspend fun setSubscriptionStatus(hasSubscription: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PREF_SUBSCRIPTION_STATUS] = hasSubscription
        }
    }

    override suspend fun clear() {
        val isOnBoardingComplete = isOnBoardingComplete.first()
        val isDarkTheme = isDarkTheme.first()
        context.dataStore.edit { it.clear() }
        setOnBoardingComplete(isOnBoardingComplete)
        setDarkTheme(isDarkTheme)
    }

    object MovaKeys {
        val PREF_PIN_CODE = stringPreferencesKey(name = "${BuildConfig.LIBRARY_PACKAGE_NAME}.pref_pin_code")
        val PREF_IS_BIOMETRY_ENABLED = booleanPreferencesKey(name = "${BuildConfig.LIBRARY_PACKAGE_NAME}.pref_is_biometry_enabled")
        val PREF_SESSION_ID = stringPreferencesKey(name = "${BuildConfig.LIBRARY_PACKAGE_NAME}.pref_session_id")
        val PREF_ONBOARDING_COMPLETE = booleanPreferencesKey(name = "${BuildConfig.LIBRARY_PACKAGE_NAME}.pref_onboarding_complete")
        val PREF_ACCOUNT_ID = intPreferencesKey(name = "${BuildConfig.LIBRARY_PACKAGE_NAME}.pref_account_id")
        val PREF_FAVOURITE_LIST_ID = intPreferencesKey(name = "${BuildConfig.LIBRARY_PACKAGE_NAME}.pref_favourite_list_id")
        val PREF_SUBSCRIPTION_STATUS = booleanPreferencesKey(name = "${BuildConfig.LIBRARY_PACKAGE_NAME}.pref_subscription_status")

        val PREF_ACCOUNT_NICKNAME = stringPreferencesKey(name = "${BuildConfig.LIBRARY_PACKAGE_NAME}.pref_account_nickname")
        val PREF_ACCOUNT_FULLNAME = stringPreferencesKey(name = "${BuildConfig.LIBRARY_PACKAGE_NAME}.pref_account_fullname")
        val PREF_ACCOUNT_EMAIL = stringPreferencesKey(name = "${BuildConfig.LIBRARY_PACKAGE_NAME}.pref_account_email")
        val PREF_ACCOUNT_PHONE_NUMBER = stringPreferencesKey(name = "${BuildConfig.LIBRARY_PACKAGE_NAME}.pref_account_phone_number")
        val PREF_ACCOUNT_PHONE_CODE = stringPreferencesKey(name = "${BuildConfig.LIBRARY_PACKAGE_NAME}.pref_account_phone_code")
        val PREF_ACCOUNT_GENDER = stringPreferencesKey(name = "${BuildConfig.LIBRARY_PACKAGE_NAME}.pref_account_gender")
        val PREF_ACCOUNT_AVATAR_PATH = stringPreferencesKey(name = "${BuildConfig.LIBRARY_PACKAGE_NAME}.pref_account_avatar_path")
        val PREF_ACCOUNT_COUNTRY = stringPreferencesKey(name = "${BuildConfig.LIBRARY_PACKAGE_NAME}.pref_account_country")

        val PREF_LOGIN_TYPE = intPreferencesKey(name = "${BuildConfig.LIBRARY_PACKAGE_NAME}.pref_login_type")
        val PREF_THEME_IS_DARK = booleanPreferencesKey(name = "${BuildConfig.LIBRARY_PACKAGE_NAME}.pref_theme_is_dark")
        val PREF_LANGUAGE_NAME = stringPreferencesKey(name = "${BuildConfig.LIBRARY_PACKAGE_NAME}.pref_language_name")
        val PREF_LANGUAGE_COUNTRY = stringPreferencesKey(name = "${BuildConfig.LIBRARY_PACKAGE_NAME}.pref_language_country")

    }
}