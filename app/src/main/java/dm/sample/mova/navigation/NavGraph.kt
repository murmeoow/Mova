package dm.sample.mova.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dm.sample.mova.domain.enums.LoginType
import dm.sample.mova.ui.screens.SplashScreen
import dm.sample.mova.ui.screens.actionmenu.ActionMenuScreen
import dm.sample.mova.ui.screens.auth.LoginScreen
import dm.sample.mova.ui.screens.auth.StartScreen
import dm.sample.mova.ui.screens.biometric.SetBiometryScreen
import dm.sample.mova.ui.screens.createaccount.ChooseInterestScreen
import dm.sample.mova.ui.screens.createaccount.CreateAccountScreen
import dm.sample.mova.ui.screens.createaccount.FillYourProfileScreen
import dm.sample.mova.ui.screens.downloads.DownloadsScreen
import dm.sample.mova.ui.screens.explorer.ExplorerSearchScreen
import dm.sample.mova.ui.screens.forgotpassword.CreateNewPasswordScreen
import dm.sample.mova.ui.screens.forgotpassword.ForgotPasswordScreen
import dm.sample.mova.ui.screens.forgotpassword.OtpScreen
import dm.sample.mova.ui.screens.home.HomeScreen
import dm.sample.mova.ui.screens.moviedetails.MovieDetailsScreen
import dm.sample.mova.ui.screens.mylist.MyListScreen
import dm.sample.mova.ui.screens.notification.NotificationScreen
import dm.sample.mova.ui.screens.onboarding.OnBoardingScreen
import dm.sample.mova.ui.screens.pincode.PinCodeScreen
import dm.sample.mova.ui.screens.player.YouTubeScreen
import dm.sample.mova.ui.screens.premium.AddNewCardScreen
import dm.sample.mova.ui.screens.premium.ReviewSummaryScreen
import dm.sample.mova.ui.screens.premium.SelectPaymentScreen
import dm.sample.mova.ui.screens.premium.SubscribeToPremiumScreen
import dm.sample.mova.ui.screens.profile.ProfileScreen
import dm.sample.mova.ui.screens.settings.DownloadSettingsScreen
import dm.sample.mova.ui.screens.settings.HelpCenterScreen
import dm.sample.mova.ui.screens.settings.LanguageSettingsScreen
import dm.sample.mova.ui.screens.settings.NotificationSettingsScreen
import dm.sample.mova.ui.screens.settings.PrivacyPolicyScreen
import dm.sample.mova.ui.screens.settings.SecuritySettingsScreen
import dm.sample.mova.ui.viewmodel.pincode.PinCodeMode

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String,
    onChangeBottomBarVisibility: (Boolean) -> Unit,
    onShowSnackBar: (Boolean, String) -> Unit,
    openShowkaseBrowser: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(route = Screen.OnBoarding.route) {
            OnBoardingScreen(
                navigateToStartScreen = {
                    navController.newRootScreen(Screen.Start)
                },
            )
        }
        composable(route = Screen.Splash.route) {
            SplashScreen(
                navigateToHome = {
                    navController.newRootScreen(Screen.Home)
                },
                navigateToStart = { navController.newRootScreen(Screen.Start) },
                navigateToPinCode = {
                    navController.newRootScreen(Screen.PinCode.passArgument(PinCodeMode.Apply.id))
                },
                navigateToOnBoarding = {
                    navController.newRootScreen(Screen.OnBoarding)
                }
            )
        }
        composable(route = Screen.Start.route) {
            StartScreen(
                onSignUpClick = { navController.navigateTo(Screen.CreateAccount) },
                onSignInClick = {
                    navController.navigateTo(Screen.Login.passArgument(LoginType.STANDARD_LOGIN))
                },
                onSignAsGuestClick = {
                    navController.navigateTo(Screen.Login.passArgument(LoginType.GUEST_LOGIN))
                },
                openShowkaseBrowser = openShowkaseBrowser
            )
        }
        composable(
            route = Screen.Login.route,
            arguments = listOf(navArgument(ARGUMENT_LOGIN_TYPE_ID) {
                type = NavType.IntType
            })
        ) {
            LoginScreen(
                navigateToPinCodeCreation = {
                    navController.newRootScreen(Screen.PinCode.passArgument(PinCodeMode.Create.id))
                },
                navigateToHome = { navController.newRootScreen(Screen.Home) },
                onBackClick = { navController.popBackStack() },
                onForgotPasswordClick = { navController.navigateTo(Screen.ForgotPassword) },
                onSignUpClick = { navController.navigateTo(Screen.CreateAccount) },
            )
        }
        composable(route = Screen.CreateAccount.route) {
            CreateAccountScreen(
                onBackClick = { navController.popBackStack() },
                onSignInClick = {
                    navController.navigateTo(Screen.Login.passArgument(LoginType.STANDARD_LOGIN))
                },
                onSignUpClick = { navController.navigateTo(Screen.ChooseInterest) }
            )
        }
        composable(route = Screen.ChooseInterest.route) {
            ChooseInterestScreen(
                onBackClick = { navController.popBackStack() },
                onContinueClick = { navController.navigateTo(Screen.FillYourProfile) },
                onSkipClick = { navController.navigateTo(Screen.FillYourProfile) }
            )
        }
        composable(route = Screen.FillYourProfile.route) {
            FillYourProfileScreen(
                onBackClick = { navController.popBackStack() },
                onContinueClick = {
                    navController.newRootScreen(Screen.PinCode.passArgument(PinCodeMode.Create.id))
                }
            )
        }
        composable(
            route = Screen.PinCode.route,
            arguments = listOf(navArgument(ARGUMENT_PIN_CODE_MODE) {
                type = NavType.IntType
            })
        ) {
            PinCodeScreen(
                navigateToBiometric = { navController.newRootScreen(Screen.Biometric.route) },
                navigateToStart = { navController.newRootScreen(Screen.Start.route) },
                navigateToHome = { navController.newRootScreen(Screen.Home.route) },
            )
        }
        composable(route = Screen.Biometric.route) {
            SetBiometryScreen(
                navigateToHome = { navController.newRootScreen(Screen.Home.route) },
            )
        }
        composable(route = Screen.ForgotPassword.route) {
            ForgotPasswordScreen(
                onBackClick = { navController.popBackStack() },
                onContinueClick = { navController.navigateTo(Screen.Otp.passArgument(it)) }
            )
        }
        composable(
            route = Screen.Otp.route,
            arguments = listOf(navArgument(ARGUMENT_OTP_STRING_KEY) {
                type = NavType.IntType
            })
        ) {
            val stringResId = it.arguments?.getInt(ARGUMENT_OTP_STRING_KEY)
            OtpScreen(
                stringResId = stringResId,
                onBackClick = { navController.popBackStack() },
                navigateToCreateNewPassword = { navController.navigateTo(Screen.CreateNewPassword) }
            )
        }
        composable(route = Screen.CreateNewPassword.route) {
            CreateNewPasswordScreen(
                onBackClick = { navController.popBackStack() },
                navigateToLogin = { navController.newRootScreen(Screen.Start) }
            )
        }
        composable(route = Screen.Home.route) {
            HomeScreen(
                navigateTo = { navController.navigateTo(it) },
                onShowSnackBar = onShowSnackBar,
            )
        }
        composable(
            route = Screen.ActionMenu.route,
            arguments = listOf(navArgument(ARGUMENT_MOVIE_CATEGORY_ID) {
                type = NavType.IntType
            })
        ) {
            ActionMenuScreen(
                onBackClick = { navController.popBackStack() },
                onSearchClick = {
                    navController.navigateTo(Screen.Explore)
                },
                navigateToMovieDetails = { navController.navigateTo(it) }
            )
        }
        composable(route = Screen.Explore.route) {
            ExplorerSearchScreen(
                onChangeBottomBarVisibility = onChangeBottomBarVisibility,
                navigateToMovieDetails = { navController.navigateTo(it) },
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(route = Screen.MyList.route) {
            MyListScreen(
                onChangeBottomBarVisibility = onChangeBottomBarVisibility,
                backToHome = { navController.popBackStack() },
                navigateToMovieDetails = { navController.navigateTo(it) },
                navigateToStartScreen = { navController.newRootScreen(Screen.Start) },
                onShowSnackBar = onShowSnackBar,
            )
        }
        composable(route = Screen.Downloads.route) {
            DownloadsScreen(
                navigateToStartScreen = { navController.newRootScreen(Screen.Start) },
                onChangeBottomBarVisibility = onChangeBottomBarVisibility,
            )
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(
                onBackClick = { navController.navigateUp() },
                navigateTo = { navController.navigateTo(it) },
                navigateToStart = { navController.newRootScreen(Screen.Start) },
                onChangeBottomBarVisibility = onChangeBottomBarVisibility,
            )
        }
        composable(
            route = Screen.MovieDetails.route,
            arguments = listOf(navArgument(ARGUMENT_MOVIE_DETAILS_ID) {
                type = NavType.IntType
            })
        ) {
            MovieDetailsScreen(
                onBackClick = { navController.popBackStack() },
                navigateToTrailerWebView = {
                    navController.navigateTo(Screen.Player.passArgument(it))
                },
                navigateToMovieDetails = {
                    navController.navigateTo(Screen.MovieDetails.passArgument(it))
                },
                navigateToStartScreen = { navController.newRootScreen(Screen.Start) },
            )
        }
        composable(route = Screen.NotificationSettings.route) {
            NotificationSettingsScreen(
                navigateOnBack = { navController.navigateUp() }
            )
        }
        composable(route = Screen.DownloadSettings.route) {
            DownloadSettingsScreen(
                navigateOnBack = { navController.navigateUp() }
            )
        }
        composable(route = Screen.SecuritySettings.route) {
            SecuritySettingsScreen(
                navigateOnBack = { navController.navigateUp() },
                onChangePin = {
                    navController.newRootScreen(Screen.PinCode.passArgument(PinCodeMode.Create.id))
                },
                onChangePassword = { /*TODO()*/ }
            )
        }

        composable(route = Screen.LanguagesSettings.route) {
            LanguageSettingsScreen(
                navigateOnBack = { navController.navigateUp() }
            )
        }

        composable(route = Screen.PrivacyPolicySettings.route) {
            PrivacyPolicyScreen(
                navigateOnBack = { navController.navigateUp() }
            )
        }

        composable(route = Screen.Notification.route) {
            NotificationScreen(
                navigateOnBack = { navController.navigateUp() },
                navigateToMovieDetails = { movieId ->
                    navController.navigateTo(Screen.MovieDetails.passArgument(movieId))
                },
                navigateToStartScreen = { navController.navigateTo(Screen.Start) }
            )
        }
        composable(route = Screen.HelpCenter.route) {
            HelpCenterScreen(
                navigateOnBack = { navController.navigateUp() }
            )
        }
        composable(
            route = Screen.Player.route,
            arguments = listOf(navArgument(ARGUMENT_TRAILER_URL_KEY) {
                type = NavType.StringType
            })
        ) {
            val argument = it.arguments?.getString(ARGUMENT_TRAILER_URL_KEY)
            YouTubeScreen(
                videoId = argument ?: "",
                onNavigateBack = { navController.navigateUp() }
            )
        }
        composable(route = Screen.SubscribeToPremium.route) {
            SubscribeToPremiumScreen(
                onBackClick = { navController.navigateUp() },
                onSelectPaymentMethodClick = {
                    navController.navigate(Screen.SelectPaymentMethod.passArgument(it, null))
                }
            )
        }
        composable(
            route = Screen.SelectPaymentMethod.route,
            arguments = listOf(
                navArgument(ARGUMENT_IS_MONTHLY_KEY) {
                    type = NavType.BoolType
                },
                navArgument(ARGUMENT_CARD_NUMBER_KEY) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val isMonthly = backStackEntry.arguments?.getBoolean(ARGUMENT_IS_MONTHLY_KEY)
            val newCardNumber by backStackEntry
                .savedStateHandle
                .getLiveData<String>(ARGUMENT_NEW_CARD_NUMBER_KEY)
                .observeAsState()
            val isChanged by backStackEntry
                .savedStateHandle
                .getLiveData<Boolean>(ARGUMENT_IS_CHANGED_CARD_NUMBER_KEY)
                .observeAsState()

            val currentCardNumber = backStackEntry.arguments?.getString(ARGUMENT_CARD_NUMBER_KEY)

            val cardText = when {
                isChanged != null && isChanged!! -> newCardNumber
                isChanged != null && isChanged!!.not() && currentCardNumber == "null" -> newCardNumber
                isChanged != null && isChanged!!.not() && currentCardNumber != "null" -> currentCardNumber
                isChanged == null && currentCardNumber != "null" -> currentCardNumber
                isChanged == null && currentCardNumber == "null" -> null
                else -> null
            }
            SelectPaymentScreen(
                cardNumber = cardText,
                isAddedNewCard = isChanged ?: false,
                onCardCreationClick = {
                    navController.navigateTo(Screen.AddNewCard)
                    backStackEntry.savedStateHandle.remove<String>(ARGUMENT_NEW_CARD_NUMBER_KEY)
                    backStackEntry.savedStateHandle.remove<String>(
                        ARGUMENT_IS_CHANGED_CARD_NUMBER_KEY
                    )
                },
                onBackClick = { navController.popBackStack() },
                onContinueClick = {
                    if (navController.previousBackStackEntry?.destination?.route == Screen.ReviewSummary.route) {
                        navController.navigate(
                            Screen.ReviewSummary.passArgument(
                                cardNumber = if (isChanged == null) {
                                    currentCardNumber ?: "null"
                                } else {
                                    newCardNumber ?: "null"
                                },
                                isMonthly = isMonthly ?: false
                            )
                        ) {
                            popUpTo(Screen.ReviewSummary.route) {
                                inclusive= true
                            }
                        }
                    } else {
                        navController.navigate(
                            Screen.ReviewSummary.passArgument(
                                cardNumber = newCardNumber ?: "null",
                                isMonthly = isMonthly ?: false
                            )
                        )
                    }
                }
            )
        }
        composable(route = Screen.AddNewCard.route) {
            AddNewCardScreen(
                onBackClick = {
                    navController.popBackStack()
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(ARGUMENT_IS_CHANGED_CARD_NUMBER_KEY, false)
                              },
                onAddClick = { cardNumber ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(ARGUMENT_NEW_CARD_NUMBER_KEY, cardNumber)
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(ARGUMENT_IS_CHANGED_CARD_NUMBER_KEY, true)
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = Screen.ReviewSummary.route,
            arguments = listOf(
                navArgument(ARGUMENT_CARD_NUMBER_KEY) {
                    type = NavType.StringType
                },
                navArgument(ARGUMENT_IS_MONTHLY_KEY) {
                    type = NavType.BoolType
                }
            )
        ) { backStackEntry ->
            val isMonthly = backStackEntry.arguments?.getBoolean(ARGUMENT_IS_MONTHLY_KEY)
            val cardNumber = backStackEntry.arguments?.getString(ARGUMENT_CARD_NUMBER_KEY)

            isMonthly?.let { monthly ->
                ReviewSummaryScreen(
                    cardNumber = cardNumber ?: "",
                    isMonthlySubscription = monthly,
                    navigateToChangePaymentMethod = {
                        navController.navigateTo(
                            Screen.SelectPaymentMethod.passArgument(
                                isMonthly,
                                cardNumber
                            )
                        )
                    },
                    navigateToProfile = { navController.navigate(Screen.Profile.route) {
                        popUpTo(Screen.Profile.route)
                    } },
                    onBackClick = {
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set(ARGUMENT_IS_CHANGED_CARD_NUMBER_KEY, false)
                        navController.popBackStack()
                     },
                )
            }
        }
    }
}