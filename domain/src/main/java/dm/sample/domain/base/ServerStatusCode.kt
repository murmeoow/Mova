package dm.sample.mova.domain.base

enum class ServerStatusCode(val code: Int) {
    SUCCESS(1), //	Success.
    ITEM_DELETED_SUCCESSFULLY(13), // The item/record was deleted successfully.
    AUTHENTICATION_FAILED(3), //	Authentication failed: You do not have permissions to access the service.
    EMPTY_USERNAME_OR_PASSWORD(26),  // You must provide a username and password.
    INVALID_USERNAME_OR_PASSWORD(30), // Invalid username and/or password: You did not provide a valid login.
    ACCOUNT_DISABLED(31), // Account disabled: Your account is no longer active.
    EMAIL_NOT_VERIFIED(32), //	Email not verified: Your email address has not been verified.
    RESOURCE_NOT_FOUND(34), //	The resource you requested could not be found.
    UNKNOWN_ERROR(-1); // if unknown see http code

    companion object {
        fun find(code: Int) = values().find { it.code == code } ?: UNKNOWN_ERROR
    }
}