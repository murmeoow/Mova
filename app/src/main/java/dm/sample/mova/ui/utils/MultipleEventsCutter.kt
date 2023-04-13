package dm.sample.mova.ui.utils

// https://al-e-shevelev.medium.com/how-to-prevent-multiple-clicks-in-android-jetpack-compose-8e62224c9c5e

internal interface MultipleEventsCutter {
    fun processEvent(event: () -> Unit)

    companion object
}

internal fun MultipleEventsCutter.Companion.get(): MultipleEventsCutter = MultipleEventsCutterImpl()

private class MultipleEventsCutterImpl : MultipleEventsCutter {
    private val now: Long
        get() = System.currentTimeMillis()

    private var lastEventTimeMs: Long = 0

    override fun processEvent(event: () -> Unit) {
        if (now - lastEventTimeMs >= 300L) {
            event.invoke()
        }
        lastEventTimeMs = now
    }
}