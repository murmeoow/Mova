package dm.sample.mova.ui.utils

import android.os.CountDownTimer
import javax.inject.Inject

class CountDownTimerUtil @Inject constructor() {
    private var timer: CountDownTimer? = null
    var onTick: ((millisUntilFinished: Long) -> Unit)? = null
    var onFinish: (() -> Unit)? = null
    var onStartTimer: (() -> Unit)? = null
    var isFinished = true

    fun startTimer(timerDuration: Long) {
        onStartTimer?.invoke()
        timer = object : CountDownTimer(timerDuration, 1_000L) {
            override fun onTick(millisUntilFinished: Long) {
                this@CountDownTimerUtil.onTick?.invoke(millisUntilFinished / 1000)
            }

            override fun onFinish() {
                this@CountDownTimerUtil.onFinish?.invoke()
                this@CountDownTimerUtil.isFinished = true
            }
        }.start()
        isFinished = false
    }

    fun cancelTimer() {
        if (timer != null) {
            timer?.cancel()
            timer = null
        }
    }
}
