package dm.sample.mova.ui.utils

import android.content.Context
import android.os.Build
import android.os.Vibrator
import androidx.annotation.IntRange

@Suppress("DEPRECATION")
fun Context.vibrate(
    duration: Long = 50,
    @IntRange(from = 1, to = 255) amplitude: Int = 2,
) {
    (getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator)?.let { vibrator ->
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(
                android.os.VibrationEffect.createOneShot(
                    duration,
                    amplitude,
                )
            )
        } else {
            vibrator.vibrate(duration)
        }
    }
}