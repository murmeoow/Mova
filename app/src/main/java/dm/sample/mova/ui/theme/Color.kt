package dm.sample.mova.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import dm.sample.mova.domain.Constants
import com.airbnb.android.showkase.annotation.ShowkaseColor

// Primary
@ShowkaseColor(name = "Primary 500", group = Constants.SHOWKASE_GROUP_COLOR)
val Primary500 = Color(0xFFE21221)
@ShowkaseColor(name = "Primary 400", group = Constants.SHOWKASE_GROUP_COLOR)
val Primary400 = Color(0xFFE8414D)
@ShowkaseColor(name = "Primary 300", group = Constants.SHOWKASE_GROUP_COLOR)
val Primary300 = Color(0xFFEE717A)
@ShowkaseColor(name = "Primary 200", group = Constants.SHOWKASE_GROUP_COLOR)
val Primary200 = Color(0xFFF3A0A6)
@ShowkaseColor(name = "Primary 100", group = Constants.SHOWKASE_GROUP_COLOR)
val Primary100 = Color(0xFFFCE7E9)

// Secondary
val Secondary500 = Color(0xFFFFD300)
val Secondary400 = Color(0xFFFFDC33)
val Secondary300 = Color(0xFFFFE566)
val Secondary200 = Color(0xFFFFED99)
val Secondary100 = Color(0xFFFFFBE6)

// Dark
@ShowkaseColor(name = "Dark 1", group = Constants.SHOWKASE_GROUP_COLOR)
val Dark1 = Color(0xFF181A20)
@ShowkaseColor(name = "Dark 2", group = Constants.SHOWKASE_GROUP_COLOR)
val Dark2 = Color(0xFF1F222A)
@ShowkaseColor(name = "Dark 3", group = Constants.SHOWKASE_GROUP_COLOR)
val Dark3 = Color(0xFF35383F)

// Background
val BackgroundRed = Color(0xFFFFF5F6)
val BackgroundPurple = Color(0xFFF4ECFF)
val BackgroundBlue = Color(0xFFEEEEEE)
val BackgroundGreen = Color(0xFFF2FFFC)
val BackgroundOrange = Color(0xFFFFF8ED)
val BackgroundPink = Color(0xFFFFF5F5)
val BackgroundYellow = Color(0xFFFFFEE0)

// Alert & Status
val Success = Color(0xFF4ADE80)
val Info = Color(0xFF246BFD)
val Warning = Color(0xFFFACC15)
val Error = Color(0xFFF75555)
val Disabled = Color(0xFFD8D8D8)
val DisabledButton = Color(0x80E21221)

// Grayscale
@ShowkaseColor(name = "Grayscale 900", group = Constants.SHOWKASE_GROUP_COLOR)
val Grayscale900 = Color(0xFF212121)
@ShowkaseColor(name = "Grayscale 800", group = Constants.SHOWKASE_GROUP_COLOR)
val Grayscale800 = Color(0xFF424242)
@ShowkaseColor(name = "Grayscale 700", group = Constants.SHOWKASE_GROUP_COLOR)
val Grayscale700 = Color(0xFF616161)
@ShowkaseColor(name = "Grayscale 600", group = Constants.SHOWKASE_GROUP_COLOR)
val Grayscale600 = Color(0xFF757575)
@ShowkaseColor(name = "Grayscale 500", group = Constants.SHOWKASE_GROUP_COLOR)
val Grayscale500 = Color(0xFF9E9E9E)
@ShowkaseColor(name = "Grayscale 400", group = Constants.SHOWKASE_GROUP_COLOR)
val Grayscale400 = Color(0xFFBDBDBD)
@ShowkaseColor(name = "Grayscale 300", group = Constants.SHOWKASE_GROUP_COLOR)
val Grayscale300 = Color(0xFFE0E0E0)
@ShowkaseColor(name = "Grayscale 200", group = Constants.SHOWKASE_GROUP_COLOR)
val Grayscale200 = Color(0xFFEEEEEE)
@ShowkaseColor(name = "Grayscale 100", group = Constants.SHOWKASE_GROUP_COLOR)
val Grayscale100 = Color(0xFFF5F5F5)
@ShowkaseColor(name = "Grayscale 50", group = Constants.SHOWKASE_GROUP_COLOR)
val Grayscale50 = Color(0xFFFAFAFA)

// Transparent
@ShowkaseColor(name = "Transparent Red", group = Constants.SHOWKASE_GROUP_COLOR)
val TransparentRed = Color(0x14E21221)
val TransparentPurple = Color(0x147210FF)
val TransparentBlue = Color(0x14335EF7)
val TransparentOrange = Color(0x14FF9800)
val TransparentYellow = Color(0x14FACC15)
val TransparentGreen = Color(0x144CAF50)
val TransparentCyan = Color(0x144CAF50)

//Gradient
private val GradientRedStart = Color(0xFFFF4451)
val GradientRed = Brush.linearGradient(
    listOf(Primary500, GradientRedStart),
    start = Offset.Infinite,
    end = Offset.Zero
)

// Others
val White = Color(0xFFFFFFFF)
val White2 = Color(0xFFFBFBFB)
val Black = Color(0xFF000000)
val RatingStarColor = Color(0x30E21221)