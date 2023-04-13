package dm.sample.mova.domain.entities

data class Account(
    val id: Int?,
    val fullname: String?,
    val nickname: String?,
    val gender: String?,
    val phone: String?,
    val phoneCode: String?,
    val email: String?,
    val country: Country? = null,
    val avatarPath: String?,
) {
    companion object {
        fun empty() = Account(null, null, null, null, null, null, null, null, null)
    }
}