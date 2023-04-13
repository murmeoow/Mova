package dm.sample.mova.ui

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import dm.sample.mova.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private lateinit var requestMultiplePermission: ActivityResultLauncher<Array<String>>

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            installSplashScreen()
        }
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Mova)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        requestMultiplePermission = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            var isGranted = false
            it.forEach { (_, granted) ->
                isGranted = granted
            }
        }

        setContent {
            requestMultiplePermission.launch(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            )
            ComposeApp()
        }
    }
}