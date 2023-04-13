package dm.sample.mova.ui.components.pincode

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dm.sample.mova.R
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.bodyXLargeMedium
import dm.sample.mova.ui.theme.bodyXLargeRegular

@Composable
fun MovaKeyBoard(
    hasLogout: Boolean? = null,
    hasDelete: Boolean,
    onOneClick: () -> Unit,
    onTwoClick: () -> Unit,
    onThreeClick: () -> Unit,
    onFourClick: () -> Unit,
    onFiveClick: () -> Unit,
    onSixClick: () -> Unit,
    onSevenClick: () -> Unit,
    onEightClick: () -> Unit,
    onNineClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onZeroClick: () -> Unit,
    onBackSpaceClick: () -> Unit,
    onSkipClick: () -> Unit = {},
    modifier: Modifier
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)
                )
                .wrapContentHeight()) {
            NumbersKeyboardRow(
                first = "1",
                second = "2",
                third = "3",
                testTagFirst = "btnOne",
                testTagSecond = "btnTwo",
                testTagThird = "btnThree",
                onFirstClick = onOneClick,
                onSecondClick = onTwoClick,
                onThirdClick = onThreeClick,
                modifier = Modifier
                    .padding(bottom = 4.dp, top = 12.dp)
                    .padding(horizontal = 12.dp)
            )
            NumbersKeyboardRow(
                first = "4",
                second = "5",
                third = "6",
                testTagFirst = "btnFour",
                testTagSecond = "btnFive",
                testTagThird = "btnSix",
                onFirstClick = onFourClick,
                onSecondClick = onFiveClick,
                onThirdClick = onSixClick,
                modifier = Modifier
                    .padding(vertical = 4.dp, horizontal = 12.dp)
            )
            NumbersKeyboardRow(
                first = "7",
                second = "8",
                third = "9",
                testTagFirst = "btnSeven",
                testTagSecond = "btnEight",
                testTagThird = "btnNine",
                onFirstClick = onSevenClick,
                onSecondClick = onEightClick,
                onThirdClick = onNineClick,
                modifier = Modifier
                    .padding(vertical = 4.dp, horizontal = 12.dp)
            )
            BottomKeyboardRow(
                hasLogOut = hasLogout,
                hasDelete = hasDelete,
                onFirstClick = if (hasLogout == true) onLogoutClick else onSkipClick,
                onSecondClick = onZeroClick,
                onThirdClick = onBackSpaceClick,
                modifier = Modifier
                    .padding(vertical = 4.dp, horizontal = 12.dp)
            )
        }
    }
}

@Composable
fun NumbersKeyboardRow(
    first: String,
    second: String,
    third: String,
    testTagFirst: String,
    testTagSecond: String,
    testTagThird: String,
    onFirstClick: () -> Unit,
    onSecondClick: () -> Unit,
    onThirdClick: () -> Unit,
    modifier: Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        KeyboardItem(
            text = first,
            onClick = onFirstClick,
            testTag = testTagFirst
        )
        KeyboardItem(
            text = second,
            onClick = onSecondClick,
            testTag = testTagSecond
        )
        KeyboardItem(
            text = third,
            onClick = onThirdClick,
            testTag = testTagThird
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BottomKeyboardRow(
    hasLogOut: Boolean? = null,
    hasDelete: Boolean,
    onFirstClick: () -> Unit,
    onSecondClick: () -> Unit,
    onThirdClick: () -> Unit,
    modifier: Modifier
) {
    val firstButtonText = if (hasLogOut != null) {
        if (hasLogOut) {
            R.string.pincode_screen_log_out
        } else {
            R.string.skip_text
        }
    } else null

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .semantics { testTagsAsResourceId = true }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = firstButtonText?.let { stringResource(id = firstButtonText) } ?: "",
                style = bodyXLargeRegular.copy(
                    lineHeight = 25.2.sp,
                    textAlign = TextAlign.Center
                ),
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .clickable(
                        onClick = { onFirstClick() },
                        interactionSource = remember { NoRippleInteractionSource() },
                        indication = null
                    )
                    .testTag("btnLogout")
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(1f)
        ) {
            KeyboardItem(
                text = "0",
                onClick = onSecondClick,
                testTag = "btnZero"
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(1f)
        ) {
            if (hasDelete) Icon(
                painter = painterResource(id = R.drawable.ic_backspace),
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .clickable(
                        onClick = { onThirdClick() },
                        interactionSource = remember { NoRippleInteractionSource() },
                        indication = null
                    ),
                contentDescription = "backspace")
        }

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun KeyboardItem(
    text: String,
    onClick: () -> Unit,
    testTag: String
) {
    Text(
        text = text,
        style = bodyXLargeMedium.copy(
            fontSize = 24.sp,
            lineHeight = 28.64.sp,
            textAlign = TextAlign.Center
        ),
        color = MaterialTheme.colors.onBackground,
        modifier = Modifier
            .clickable(
                onClick = { onClick() },
                interactionSource = remember { NoRippleInteractionSource() },
                indication = null
            )
            .padding(vertical = 13.5.dp, horizontal = 50.dp)
            .semantics { testTagsAsResourceId = true }
            .testTag(testTag)
    )
}
