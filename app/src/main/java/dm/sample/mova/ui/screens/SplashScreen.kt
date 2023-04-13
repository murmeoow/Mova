package dm.sample.mova.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.ui.components.MovaCircularProgressBar
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.viewmodel.splash.SplashNavEvent
import dm.sample.mova.ui.viewmodel.splash.SplashViewModel
import com.airbnb.android.showkase.annotation.ShowkaseComposable

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    navigateToStart: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToPinCode: () -> Unit,
    navigateToOnBoarding: () -> Unit,
) {
    LaunchedEffect(key1 = true) {
        viewModel.fetch()
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                SplashNavEvent.ApplyPinCode -> navigateToPinCode()
                SplashNavEvent.Home -> navigateToHome()
                SplashNavEvent.Start -> navigateToStart()
                SplashNavEvent.OnBoarding -> navigateToOnBoarding()
            }
        }
    }

    SplashScreenContent()

}

@Composable
private fun SplashScreenContent() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        val (centerLogo, progressBar) = createRefs()

        Image(painter = painterResource(id = R.drawable.ic_mova_logo),
            contentDescription = "Logo",
            modifier = Modifier.constrainAs(centerLogo) {
                centerHorizontallyTo(parent)
                centerVerticallyTo(parent)
            }
        )
        MovaCircularProgressBar(
            modifier = Modifier.constrainAs(progressBar) {
                centerHorizontallyTo(parent)
                top.linkTo(centerLogo.bottom, margin = 100.dp)
            }
        )
    }
}


@ShowkaseComposable(
    name = "Splash Screen",
    group = Constants.SHOWKASE_GROUP_SCREENS,
)
@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewSplashScreen() {
    MovaTheme {
        SplashScreenContent()
    }
}