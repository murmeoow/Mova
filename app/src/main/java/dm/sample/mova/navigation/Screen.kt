package dm.sample.mova.navigation

import dm.sample.mova.domain.enums.LoginType

const val ARGUMENT_PIN_CODE_MODE = "pincode_mode"
const val ARGUMENT_MOVIE_CATEGORY_ID = "movie_category_id"
const val ARGUMENT_LOGIN_TYPE_ID = "login_type_id"
const val ARGUMENT_MOVIE_DETAILS_ID = "movie_details_id"
const val ARGUMENT_TRAILER_URL_KEY = "trailer_url_key"
const val ARGUMENT_OTP_STRING_KEY = "otp_string_key"
const val ARGUMENT_CARD_NUMBER_KEY = "card_number_key"
const val ARGUMENT_NEW_CARD_NUMBER_KEY = "new_card_number_key"
const val ARGUMENT_IS_CHANGED_CARD_NUMBER_KEY = "is_changed_card_number_key"
const val ARGUMENT_IS_MONTHLY_KEY = "is_monthly_key"

sealed class Screen(val route: String) {
    object OnBoarding : Screen(route = "onboarding")
    object Splash : Screen(route = "splash_screen")

    object Start : Screen(route = "start_screen")
    object Login : Screen(route = "login_screen/{$ARGUMENT_LOGIN_TYPE_ID}") {
        fun passArgument(loginType: LoginType) = "login_screen/${loginType.ordinal}"
    }
    object ForgotPassword : Screen(route = "forgot_password_screen")
    object Otp : Screen(route = "otp_screen/{$ARGUMENT_OTP_STRING_KEY}") {
        fun passArgument(stringId: Int) = "otp_screen/$stringId"
    }
    object CreateNewPassword : Screen(route = "create_new_password_screen")
    object CreateAccount : Screen(route = "create_account_screen")
    object ChooseInterest : Screen(route = "choose_interest_screen")
    object FillYourProfile : Screen(route = "fill_your_profile_screen")
    object PinCode : Screen(route = "pincode_screen/{$ARGUMENT_PIN_CODE_MODE}") {
        fun passArgument(modeId: Int) = "pincode_screen/$modeId"
    }
    object Biometric : Screen(route = "biometric_screen")
    object Home : Screen(route = "home_screen")
    object Explore : Screen(route = "explore_screen/{$ARGUMENT_MOVIE_CATEGORY_ID}"){
        fun passArgument(categoryId: Int = 0) = "explore_screen/$categoryId"
    }
    object Downloads : Screen(route = "downloads_screen")

    object Profile : Screen(route = "profile_screen")
    object NotificationSettings : Screen(route = "settings_notification_screen")
    object DownloadSettings : Screen(route = "settings_download_screen")
    object SecuritySettings : Screen(route = "settings_security_screen")
    object LanguagesSettings : Screen(route = "settings_languages_screen")
    object PrivacyPolicySettings : Screen(route = "settings_privacy_policy")
    object HelpCenter: Screen(route = "settings_help_center_screen")
    object Notification: Screen(route = "notification_screen")

    object ActionMenu : Screen(route = "action_menu_screen/{$ARGUMENT_MOVIE_CATEGORY_ID}") {
        fun passArgument(categoryId: Int) = "action_menu_screen/$categoryId"
    }
    object MyList : Screen(route = "my_list_screen")
    object MovieDetails : Screen(route = "movie_details_screen/{$ARGUMENT_MOVIE_DETAILS_ID}") {
        fun passArgument(movieId: Long) = "movie_details_screen/$movieId"
    }
    object Player : Screen(route = "player_screen/{$ARGUMENT_TRAILER_URL_KEY}") {
        fun passArgument(key: String) = "player_screen/$key"
    }
    object SubscribeToPremium: Screen(route = "subscribe_to_premium_screen")
    object SelectPaymentMethod: Screen(route = "select_payment_method_screen/{$ARGUMENT_IS_MONTHLY_KEY}/{$ARGUMENT_CARD_NUMBER_KEY}") {
        fun passArgument(isMonthly: Boolean, cardNumber: String?): String {
            return "select_payment_method_screen/$isMonthly/$cardNumber"
        }
    }
    object AddNewCard: Screen(route = "add_new_card_screen")
    object ReviewSummary: Screen(
        route = "review_summary_screen/{$ARGUMENT_CARD_NUMBER_KEY}/{$ARGUMENT_IS_MONTHLY_KEY}"
    ) {
        fun passArgument(cardNumber: String, isMonthly: Boolean)
            = "review_summary_screen/$cardNumber/$isMonthly"
    }
}