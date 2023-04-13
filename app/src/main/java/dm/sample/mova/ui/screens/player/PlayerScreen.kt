package dm.sample.mova.ui.screens.player

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import dm.sample.mova.BuildConfig
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragmentXKt

@Composable
fun YouTubeScreen(
    videoId: String,
    onNavigateBack: () -> Unit,
) {
    val ctx = LocalContext.current

    AndroidView(
        factory = {
            val fm = (ctx as FragmentActivity).supportFragmentManager
            val view = FragmentContainerView(it).apply {
                id = androidx.fragment.R.id.fragment_container_view_tag
            }
            val fragment = YouTubePlayerSupportFragmentXKt.newInstance().apply {
                initialize(
                    youtubeDeveloperKey = BuildConfig.YOUTUBE_API_KEY,
                    initializedListener = OnInitializedListener(ctx, videoId),
                    onNavigateBack = onNavigateBack
                )
            }
            fm.commit {
                setReorderingAllowed(true)
                add(androidx.fragment.R.id.fragment_container_view_tag, fragment)
            }
            view
        },
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
    )
}

private class OnInitializedListener(
    private val context: Context,
    private val videoId: String,
) : YouTubePlayer.OnInitializedListener {
    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider,
        result: YouTubeInitializationResult
    ) {
        Toast.makeText(
            context,
            "Error initializing video ${result.name}",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider,
        player: YouTubePlayer,
        wasRestored: Boolean
    ) {
        player.setShowFullscreenButton(false)
        if (!wasRestored) {
            player.cueVideo(videoId)
        }
    }
}