package dm.sample.data.repositories

import dm.sample.data.local.prefsstore.MovaDataStore
import dm.sample.data.remote.ServerApi
import dm.sample.data.remote.dtos.response.toDomain
import dm.sample.data.repositories.base.BaseRepository
import dm.sample.data.utils.CountryUtils
import dm.sample.mova.domain.entities.Account
import dm.sample.mova.domain.repositories.AccountRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val serverApi: ServerApi,
    private val dataStore: MovaDataStore,
    gson: Gson,
) : AccountRepository, BaseRepository(gson) {

    override suspend fun fetchAccountDetails() = doRequest {
        val sessionId = dataStore.getSessionId.first()

        val response = serverApi.getAccountDetails(sessionId)
        dataStore.saveAccountId(response.id)
        dataStore.saveAccountFullname(response.name)
        dataStore.saveAccountNickName(response.username)
        response.toDomain()
    }

    override suspend fun updateAccount(account: Account): Account {
        account.apply {
            if (nickname.isNullOrBlank().not()) dataStore.saveAccountNickName(nickname!!)
            if (fullname.isNullOrBlank().not()) dataStore.saveAccountFullname(fullname!!)
            if (gender.isNullOrBlank().not()) dataStore.saveAccountGender(gender!!)
            if (phone.isNullOrBlank().not()) dataStore.saveAccountPhoneNumber(phone!!)
            if (phoneCode.isNullOrBlank().not()) dataStore.saveAccountPhoneCode(phoneCode!!)
            if (email.isNullOrBlank().not()) dataStore.saveAccountEmail(email!!)
            if (avatarPath.isNullOrBlank().not()) dataStore.saveAccountAvatar(avatarPath!!)
            if (country != null) dataStore.saveAccountCountryCode(country!!.nameCode)
            if (id != null && id!! > 0) dataStore.saveAccountId(id!!)
        }
        return getAccount()
    }

    override suspend fun updateAccountAvatar(path: String) {
        dataStore.saveAccountAvatar(path)
    }

    override suspend fun getAccount(): Account {
        val fullname = dataStore.getAccountFullname.first()
        val nickname = dataStore.getAccountNickname.first()
        val gender = dataStore.getAccountGender.first()
        val phone = dataStore.getAccountPhoneNumber.first()
        val phoneCode = dataStore.getAccountPhoneCode.first()
        val email = dataStore.getAccountEmail.first()
        val id = dataStore.getAccountId.first()
        val avatarPath = dataStore.getAccountAvatar.first()
        val countryCode = dataStore.getAccountCountryCode.first()
        val country = CountryUtils.findCountryByNameCode(countryCode ?: "")

        return Account(id, fullname, nickname, gender, phone, phoneCode, email, country, avatarPath)
    }

    override suspend fun getCreatedList(accountId: Int) = doRequest {
        val sessionId = dataStore.getSessionId.first()
        val response = serverApi.getCreatedLists(accountId, sessionId)
        val list = response.toDomain()
        dataStore.saveFavouriteListId(list.createdLists[0].id.toInt())
        list
    }

    override suspend fun getSubscriptionStatus(): Boolean {
        return dataStore.getSubscriptionStatus.first()
    }

    override suspend fun setSubscriptionStatus(hasSubscription: Boolean) {
        dataStore.setSubscriptionStatus(hasSubscription)
    }
}