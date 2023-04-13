@file:OptIn(ExperimentalPagerApi::class)

package dm.sample.mova.ui.screens.onboarding

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.ui.components.FullScreen
import dm.sample.mova.ui.components.MovaRedButton
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.components.onboarding.MovaVerticalGradient
import dm.sample.mova.ui.components.onboarding.WormPageIndicator
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.Dark1
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.Grayscale300
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.bodyXLargeMedium
import dm.sample.mova.ui.utils.isLastPage
import dm.sample.mova.ui.viewmodel.OnBoardingViewModel
import com.airbnb.android.showkase.annotation.ShowkaseComposable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel = hiltViewModel(),
    navigateToStartScreen: () -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.isCompleteOnBoarding.collect { isComplete ->
            if (isComplete) navigateToStartScreen()
        }
    }

    val pages = listOf(
        OnBoardingPage(
            title = stringResource(R.string.onboarding_page1_title),
            subTitle = stringResource(R.string.onboarding_page1_subtitle),
            hasButton = false,
        ),
        OnBoardingPage(
            title = stringResource(R.string.onboarding_page2_title),
            subTitle = stringResource(R.string.onboarding_page2_subtitle),
            hasButton = false,
        ),
        OnBoardingPage(
            title = stringResource(R.string.onboarding_page3_title),
            subTitle = stringResource(R.string.onboarding_page3_subtitle),
            hasButton = true,
        ),
    )

    FullScreen {
        OnBoardingContent(
            pages = pages,
            onGetStarted = viewModel::onGetStarted,
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun OnBoardingContent(
    pages: List<OnBoardingPage>,
    onGetStarted: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Dark1)
            .semantics { testTagsAsResourceId = true },
    ) {
        val pagerState = rememberPagerState()

        OnBoardingPageBackground(pagerState.currentPage)

        MovaVerticalGradient(listOf(Color.Transparent, Dark1))

        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
        ) {
            HorizontalPager(
                count = pages.size,
                state = pagerState,
                modifier = Modifier.weight(1f),
            ) { index ->
                OnBoardingPageContent(page = pages[index])
            }
            WormPageIndicator(
                modifier = Modifier.padding(16.dp),
                totalPages = pages.size,
                currentPage = pagerState.currentPage,
                color = Grayscale300,
                activeColor = Primary500,
            )

            val skipButtonAlpha: Float by animateFloatAsState(targetValue = if (pagerState.currentPage != 2) 1f else 0f)
            val getStartedButtonAlpha: Float by animateFloatAsState(targetValue = if (pagerState.currentPage == 2) 1f else 0f)

            if (pagerState.isLastPage()) {
                MovaRedButton(
                    buttonText = R.string.onboarding_get_started_button,
                    onClick = onGetStarted,
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(getStartedButtonAlpha)
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 36.dp)
                        .testTag("btnGetStarted"),
                )
            } else {
                MovaVerticalSpacer(height = 24.dp)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .alpha(skipButtonAlpha)
                        .padding(bottom = 32.dp),
                    contentAlignment = Alignment.BottomStart,
                ) {
                    TextButton(
                        onClick = onGetStarted,
                        interactionSource = remember { NoRippleInteractionSource() },
                        modifier = Modifier.testTag("btnSkip")
                    ) {
                        Text(
                            text = stringResource(R.string.onboarding_skip),
                            style = MaterialTheme.typography.h4,
                            color = Color.White,
                        )
                    }
                }
            }
            MovaVerticalSpacer(height = 24.dp)
        }

    }
}

@Composable
private fun OnBoardingPageContent(page: OnBoardingPage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = page.title,
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center,
            color = Color.White,
        )
        MovaVerticalSpacer(height = 24.dp)
        Text(
            text = page.subTitle,
            style = bodyXLargeMedium,
            textAlign = TextAlign.Center,
            color = Color.White,
        )
        MovaVerticalSpacer(height = 24.dp)
    }
}

@Composable
private fun OnBoardingPageBackground(page: Int) {
    val scaleAnimImage1: Float by animateFloatAsState(targetValue = if (page == 0) 1f else 0f)
    val scaleAnimImage2: Float by animateFloatAsState(targetValue = if (page == 1) 1f else 0f)
    val scaleAnimImage3: Float by animateFloatAsState(targetValue = if (page == 2) 1f else 0f)


    Image(
        painter = painterResource(R.drawable.bg_onboarding_1),
        contentDescription = "background image",
        modifier = Modifier
            .fillMaxSize()
            .offset()
            .scale(scaleAnimImage1),
        contentScale = ContentScale.Crop,
    )

    Image(
        painter = painterResource(R.drawable.bg_onboarding_2),
        contentDescription = "background image",
        modifier = Modifier
            .fillMaxSize()
            .scale(scaleAnimImage2),
        contentScale = ContentScale.Crop,
    )

    Image(
        painter = painterResource(R.drawable.bg_onboarding_3),
        contentDescription = "background image",
        modifier = Modifier
            .fillMaxSize()
            .scale(scaleAnimImage3),
        contentScale = ContentScale.Crop,
    )
}

@ShowkaseComposable(
    name = "Onboard Screen",
    group = Constants.SHOWKASE_GROUP_SCREENS,
)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewOnBoardingScreen() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        Surface {
            val pages = listOf(
                OnBoardingPage(
                    title = "Welcome Mova",
                    subTitle = "The best movie streaming app of the century to make your days great!",
                    hasButton = false,
                ),
            )
            OnBoardingContent(
                pages = pages,
                onGetStarted = { },
            )
        }
    }
}