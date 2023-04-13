package dm.sample.mova.ui.screens.createaccount

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import dm.sample.mova.R
import dm.sample.mova.domain.entities.Genre
import dm.sample.mova.ui.components.HeaderWithButtonAndTitle
import dm.sample.mova.ui.components.MovaLoadingScreen
import dm.sample.mova.ui.components.MovaTwoButtons
import dm.sample.mova.ui.components.createaccount.InterestList
import dm.sample.mova.ui.theme.bodyXLargeRegular
import dm.sample.mova.ui.viewmodel.createaccount.ChooseInterestViewModel

@Composable
fun ChooseInterestScreen(
    onBackClick: () -> Unit,
    onContinueClick: () -> Unit,
    onSkipClick: () -> Unit
) {
    val viewModel = hiltViewModel<ChooseInterestViewModel>()
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.fetchGenres()
    }

    if (state.isLoading) {
        MovaLoadingScreen()
    } else {
        ChooseInterestScreenContent(
            interestList = state.interestList,
            onBackClick = onBackClick,
            onSkipClick = onSkipClick,
            onContinueClick = onContinueClick,
        )
    }
}

@Composable
fun ChooseInterestScreenContent(
    interestList: List<Genre>,
    onBackClick: () -> Unit,
    onSkipClick: () -> Unit,
    onContinueClick: () -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)

    ) {
        val (header, subtitle, list, buttons) = createRefs()
        HeaderWithButtonAndTitle(
            title = R.string.choose_your_interest_title,
            onBackClick = onBackClick,
            modifier = Modifier
                .constrainAs(header) {
                    top.linkTo(parent.top)
                }
        )
        Text(
            text = stringResource(id = R.string.choose_your_interest_subtitle),
            style = bodyXLargeRegular.copy(
                lineHeight = 25.2.sp,
            ),
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .constrainAs(subtitle) {
                    top.linkTo(header.bottom, 12.dp)
                    start.linkTo(parent.start)
                }
        )
        InterestList(
            interestList = interestList + interestList,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .constrainAs(list) {
                    top.linkTo(subtitle.bottom, 12.dp)
                }
        )
        MovaTwoButtons(
            firstText = R.string.skip_text,
            secondText = R.string.continue_text,
            onFirstClick = onSkipClick,
            onSecondClick = onContinueClick,
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .constrainAs(buttons) {
                    bottom.linkTo(parent.bottom)
                }
                .padding(horizontal = 24.dp)
                .padding(bottom = 12.dp)
        )
    }
}

@Preview
@Composable
fun ChooseInterestScreenPreview() {
    val interestList = List(45) {
        Genre(0, "Action")
    }
    ChooseInterestScreenContent(
        interestList = interestList,
        onBackClick = { /**/ },
        onSkipClick = { /**/ }) {
    }
}