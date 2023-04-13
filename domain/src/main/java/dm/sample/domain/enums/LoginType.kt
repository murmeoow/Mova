package dm.sample.mova.domain.enums

enum class LoginType {
    STANDARD_LOGIN,
    GUEST_LOGIN;

    companion object {
        fun fromOrdinal(ordinal: Int) = values().first { it.ordinal == ordinal}
    }
}