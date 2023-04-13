package dm.sample.mova.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dm.sample.mova.R
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.Grayscale500
import dm.sample.mova.ui.theme.Grayscale900
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.TransparentRed
import dm.sample.mova.ui.theme.White
import dm.sample.mova.ui.theme.bodyLargeSemiBold
import dm.sample.mova.ui.theme.bodyMediumRegular
import dm.sample.mova.ui.utils.isDarkTheme

private const val PASSWORD_MASK = '\u2B24'
private const val PASSWORD_REGEX = "[^A-Za-z0-9\$&+,:;=\\\\?@#|/'<>.^*()%!-]"

@Composable
fun EmailTextField(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes labelTextId: Int,
    isError: Boolean = false,
    modifier: Modifier,
) {
    val filterRegex = Regex(PASSWORD_REGEX)
    SampleTextField(
        value = value,
        isError = isError,
        backgroundColor = if (isError) TransparentRed else MaterialTheme.colors.surface,
        onValueChange = { if (it.contains(filterRegex).not()) onValueChange(it) },
        leadingIcon = R.drawable.ic_profile_filled,
        placeholderId = labelTextId,
        modifier = modifier
    )
}

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes placeholderTextId: Int? = null,
    isError: Boolean = false,
    modifier: Modifier,
) {
    var isVisible by rememberSaveable { mutableStateOf(false) }
    val trailingIcon = if (isVisible) R.drawable.ic_show else R.drawable.ic_hide
    val filterRegex = Regex(PASSWORD_REGEX)
    SampleTextField(
        value = value,
        onValueChange = { if (it.contains(filterRegex).not()) onValueChange(it) },
        leadingIcon = R.drawable.ic_lock,
        trailingIcon = trailingIcon,
        placeholderId = placeholderTextId,
        isError = isError,
        backgroundColor = if (isError) TransparentRed else MaterialTheme.colors.surface,
        visualTransformation = if (isVisible) VisualTransformation.None
        else PasswordVisualTransformation(PASSWORD_MASK),
        trailingIconClick = { isVisible = isVisible.not() },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SampleTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    readOnly: Boolean = false,
    focusRequester: FocusRequester = FocusRequester(),
    @StringRes placeholderId: Int? = null,
    @DrawableRes leadingIcon: Int? = null,
    @DrawableRes trailingIcon: Int? = null,
    trailingIconTint: Color? = null,
    leadingIconTint: Color? = null,
    backgroundColor: Color = MaterialTheme.colors.surface,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
    trailingIconClick: (() -> Unit)? = null,
    isError: Boolean = false,
    onClick: () -> Unit = { },
) {
    var hasFocus by rememberSaveable {
        mutableStateOf(false)
    }
    val isSystemInDarkTheme = isDarkTheme()
    val textFieldBackgroundColor = if (hasFocus) TransparentRed else backgroundColor

    // init icon Colors
    val iconsColor: Color = if (isError || hasFocus) {
        Primary500
    } else if (isSystemInDarkTheme) {
        if (value.isNotEmpty()) White else Grayscale500
    } else {
        if (value.isNotEmpty()) Grayscale900 else Grayscale500
    }

    // init Text Color
    val textColor: Color = if (isSystemInDarkTheme) {
        if (value.isEmpty()) Grayscale500 else White
    } else {
        if (value.isEmpty()) Grayscale500 else Grayscale900
    }

    val leadIcon: (@Composable () -> Unit)? = if (leadingIcon != null) {
        {
            Icon(
                painter = painterResource(id = leadingIcon),
                contentDescription = "",
                tint = leadingIconTint ?: iconsColor,
                modifier = Modifier
                    .padding(
                        start = 6.dp,
                        top = 15.dp,
                        bottom = 15.dp
                    )
            )
        }
    } else null

    val trailIcon: (@Composable () -> Unit)? = if (trailingIcon != null) {
        {
            Icon(
                painter = painterResource(id = trailingIcon),
                contentDescription = "",
                tint = trailingIconTint ?: iconsColor,
                modifier = Modifier
                    .padding(end = 16.dp, top = 8.dp, bottom = 8.dp)
                    .clickable {
                        trailingIconClick?.invoke()
                    }
            )
        }
    } else null

    val interactionSource = remember {
        if (readOnly) NoRippleInteractionSource() else MutableInteractionSource()
    }

    val colors = TextFieldDefaults.outlinedTextFieldColors(
        textColor = textColor,
        leadingIconColor = Primary500,
        unfocusedLabelColor = textColor,
        focusedLabelColor = Primary500,
        unfocusedBorderColor = MaterialTheme.colors.surface,
        focusedBorderColor = Primary500,
        backgroundColor = textFieldBackgroundColor,
        placeholderColor = Grayscale500,
        disabledTextColor = textColor,
    )

    Box(modifier = modifier
        .height(52.dp)
        .fillMaxWidth()
        .background(color = textFieldBackgroundColor, shape = RoundedCornerShape(12.dp))
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = bodyLargeSemiBold.copy(lineHeight = 19.6.sp, color = textColor),
            interactionSource = interactionSource,
            enabled = !readOnly,
            singleLine = true,
            cursorBrush = SolidColor(Primary500),
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = onClick)
                .focusRequester(focusRequester)
                .onFocusChanged { hasFocus = it.hasFocus },
        ) {
            TextFieldDefaults.OutlinedTextFieldDecorationBox(
                value = value,
                visualTransformation = visualTransformation,
                innerTextField = it,
                singleLine = true,
                enabled = !readOnly,
                placeholder = {
                    Text(
                        text = placeholderId?.let { stringResource(id = placeholderId) } ?: "",
                        style = bodyMediumRegular
                    )
                },
                leadingIcon = leadIcon,
                trailingIcon = trailIcon,
                interactionSource = interactionSource,
                contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                    start = 15.dp, end = 8.dp
                ),
                border = {
                    TextFieldDefaults.BorderBox(
                        enabled = readOnly.not(),
                        isError = false,
                        colors = colors,
                        interactionSource = interactionSource,
                        shape = RoundedCornerShape(12.dp),
                        unfocusedBorderThickness = 1.dp,
                        focusedBorderThickness = 1.dp
                    )
                },
                colors = colors
            )
        }
    }
}

@Composable
fun DigitTextField(
    value: String,
    onValueChange: (String) -> Unit,
    readOnly: Boolean,
    hasError: Boolean,
    isInPinCodeMode: Boolean = true,
    modifier: Modifier = Modifier,
) {
    var hasFocus by rememberSaveable {
        mutableStateOf(false)
    }
    val backgroundColor = if (hasFocus || hasError) TransparentRed else MaterialTheme.colors.surface
    val pinCodeMask = if (isDarkTheme()) '\u26AA' else '\u26AB'
    val visualTransformationMethod = if (isInPinCodeMode) {
        PasswordVisualTransformation(pinCodeMask)
    } else {
        VisualTransformation.None
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        readOnly = readOnly,
        textStyle = MaterialTheme.typography.h4.copy(
            lineHeight = 19.6.sp,
            textAlign = TextAlign.Center
        ),
        isError = hasError,
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colors.onBackground,
            unfocusedBorderColor = MaterialTheme.colors.secondary,
            focusedBorderColor = Primary500,
            errorBorderColor = Primary500,
            backgroundColor = backgroundColor,
        ),
        singleLine = true,
        visualTransformation = visualTransformationMethod,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Next,
        ),
        modifier = modifier
            .onFocusChanged { hasFocus = it.hasFocus }
            .clickable(enabled = false, onClick = {})
    )
}