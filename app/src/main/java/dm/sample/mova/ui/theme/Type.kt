package dm.sample.mova.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import com.airbnb.android.showkase.annotation.ShowkaseTypography

private val Urbanist = FontFamily(
    Font(R.font.urbanist_regular, FontWeight.Normal),
    Font(R.font.urbanist_medium, FontWeight.Medium),
    Font(R.font.urbanist_semi_bold, FontWeight.SemiBold),
    Font(R.font.urbanist_bold, FontWeight.Bold),
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = Urbanist,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h1 = TextStyle(
        fontFamily = Urbanist,
        fontWeight = FontWeight.Bold,
        fontSize = 48.sp
    ),
    h2 = TextStyle(
        fontFamily = Urbanist,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp
    ),
    h3 = TextStyle(
        fontFamily = Urbanist,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    h4 = TextStyle(
        fontFamily = Urbanist,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    h5 = TextStyle(
        fontFamily = Urbanist,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    h6 = TextStyle(
        fontFamily = Urbanist,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
)
// Body Bold
@ShowkaseTypography(name = "bodyXLargeBold", group = Constants.SHOWKASE_GROUP_TYPO)
val bodyXLargeBold = TextStyle(
    fontFamily = Urbanist,
    fontWeight = FontWeight.Bold,
    fontSize = 18.sp,
    letterSpacing = 0.2.sp,
)
@ShowkaseTypography(name = "bodyLargeBold", group = Constants.SHOWKASE_GROUP_TYPO)
val bodyLargeBold = TextStyle(
    fontFamily = Urbanist,
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp,
    letterSpacing = 0.2.sp,
)
@ShowkaseTypography(name = "bodyMediumBold", group = Constants.SHOWKASE_GROUP_TYPO)
val bodyMediumBold = TextStyle(
    fontFamily = Urbanist,
    fontWeight = FontWeight.Bold,
    fontSize = 14.sp,
    letterSpacing = 0.2.sp,
)
@ShowkaseTypography(name = "bodySmallBold", group = Constants.SHOWKASE_GROUP_TYPO)
val bodySmallBold = TextStyle(
    fontFamily = Urbanist,
    fontWeight = FontWeight.Bold,
    fontSize = 12.sp,
    letterSpacing = 0.2.sp,
)
@ShowkaseTypography(name = "bodyXSmallBold", group = Constants.SHOWKASE_GROUP_TYPO)
val bodyXSmallBold = TextStyle(
    fontFamily = Urbanist,
    fontWeight = FontWeight.Bold,
    fontSize = 10.sp,
    letterSpacing = 0.2.sp,
)


// Body Semi-bold
@ShowkaseTypography(name = "bodyXLargeSemiBold", group = Constants.SHOWKASE_GROUP_TYPO)
val bodyXLargeSemiBold = TextStyle(
    fontFamily = Urbanist,
    fontWeight = FontWeight.SemiBold,
    fontSize = 18.sp,
    letterSpacing = 0.2.sp,
)
@ShowkaseTypography(name = "bodyLargeSemiBold", group = Constants.SHOWKASE_GROUP_TYPO)
val bodyLargeSemiBold = TextStyle(
    fontFamily = Urbanist,
    fontWeight = FontWeight.SemiBold,
    fontSize = 16.sp,
    letterSpacing = 0.2.sp,
)
@ShowkaseTypography(name = "bodyMediumSemiBold", group = Constants.SHOWKASE_GROUP_TYPO)
val bodyMediumSemiBold = TextStyle(
    fontFamily = Urbanist,
    fontWeight = FontWeight.SemiBold,
    fontSize = 14.sp,
    letterSpacing = 0.2.sp,
)
@ShowkaseTypography(name = "bodySmallSemiBold", group = Constants.SHOWKASE_GROUP_TYPO)
val bodySmallSemiBold = TextStyle(
    fontFamily = Urbanist,
    fontWeight = FontWeight.SemiBold,
    fontSize = 12.sp,
    letterSpacing = 0.2.sp,
)
@ShowkaseTypography(name = "bodyXSmallSemiBold", group = Constants.SHOWKASE_GROUP_TYPO)
val bodyXSmallSemiBold = TextStyle(
    fontFamily = Urbanist,
    fontWeight = FontWeight.SemiBold,
    fontSize = 10.sp,
    letterSpacing = 0.2.sp,
)
// Body Medium
@ShowkaseTypography(name = "bodyXLargeMedium", group = Constants.SHOWKASE_GROUP_TYPO)
val bodyXLargeMedium = TextStyle(
    fontFamily = Urbanist,
    fontWeight = FontWeight.Medium,
    fontSize = 18.sp,
    letterSpacing = 0.2.sp,
)
@ShowkaseTypography(name = "bodyLargeMedium", group = Constants.SHOWKASE_GROUP_TYPO)
val bodyLargeMedium = TextStyle(
    fontFamily = Urbanist,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    letterSpacing = 0.2.sp,
)
@ShowkaseTypography(name = "bodyMediumMedium", group = Constants.SHOWKASE_GROUP_TYPO)
val bodyMediumMedium = TextStyle(
    fontFamily = Urbanist,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    letterSpacing = 0.2.sp,
)
@ShowkaseTypography(name = "bodySmallMedium", group = Constants.SHOWKASE_GROUP_TYPO)
val bodySmallMedium = TextStyle(
    fontFamily = Urbanist,
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp,
    letterSpacing = 0.2.sp,
)
@ShowkaseTypography(name = "bodyXSmallMedium", group = Constants.SHOWKASE_GROUP_TYPO)
val bodyXSmallMedium = TextStyle(
    fontFamily = Urbanist,
    fontWeight = FontWeight.Medium,
    fontSize = 10.sp,
    letterSpacing = 0.2.sp,
)


// Body Regular
@ShowkaseTypography(name = "bodyXLargeRegular", group = Constants.SHOWKASE_GROUP_TYPO)
val bodyXLargeRegular = TextStyle(
    fontFamily = Urbanist,
    fontWeight = FontWeight.Normal,
    fontSize = 18.sp,
    letterSpacing = 0.2.sp,
)
@ShowkaseTypography(name = "bodyLargeRegular", group = Constants.SHOWKASE_GROUP_TYPO)
val bodyLargeRegular = TextStyle(
    fontFamily = Urbanist,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    letterSpacing = 0.2.sp,
)
@ShowkaseTypography(name = "bodyMediumRegular", group = Constants.SHOWKASE_GROUP_TYPO)
val bodyMediumRegular = TextStyle(
    fontFamily = Urbanist,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    letterSpacing = 0.2.sp,
)
@ShowkaseTypography(name = "bodySmallRegular", group = Constants.SHOWKASE_GROUP_TYPO)
val bodySmallRegular = TextStyle(
    fontFamily = Urbanist,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    letterSpacing = 0.2.sp,
)
@ShowkaseTypography(name = "bodyXSmallRegular", group = Constants.SHOWKASE_GROUP_TYPO)
val bodyXSmallRegular = TextStyle(
    fontFamily = Urbanist,
    fontWeight = FontWeight.Normal,
    fontSize = 10.sp,
    letterSpacing = 0.2.sp,
)

val enterAsGuest = TextStyle(
    fontFamily = Urbanist,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 16.sp,
    letterSpacing = 0.2.sp,
    lineHeight = 22.4.sp
)

val addCardTitle = TextStyle(
    fontFamily = Urbanist,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 18.sp,
    letterSpacing = 0.2.sp,
    lineHeight = 21.6.sp
)