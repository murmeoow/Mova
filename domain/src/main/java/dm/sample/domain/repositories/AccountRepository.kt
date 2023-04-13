package dm.sample.mova.domain.repositories

import dm.sample.mova.domain.entities.Account
import dm.sample.mova.domain.entities.response.GetAccountDetailsResponse
import dm.sample.mova.domain.entities.response.GetCreatedListsResponse

interface AccountRepository {

    suspend fun fetchAccountDetails(): GetAccountDetailsResponse

    suspend fun updateAccount(account: Account): Account

    suspend fun updateAccountAvatar(path: String)

    suspend fun getAccount(): Account

    suspend fun getCreatedList(accountId: Int): GetCreatedListsResponse

    suspend fun getSubscriptionStatus(): Boolean

    suspend fun setSubscriptionStatus(hasSubscription: Boolean)
}