package dm.sample.mova.ui.viewmodel.splash

sealed class SplashNavEvent {
    object ApplyPinCode : SplashNavEvent()
    object Start : SplashNavEvent()
    object Home : SplashNavEvent()
    object OnBoarding: SplashNavEvent()
}