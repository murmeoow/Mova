package dm.sample.mova.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dm.sample.mova.BuildConfig
import dm.sample.mova.navigation.Screen
import dm.sample.mova.navigation.SetupNavGraph
import dm.sample.mova.ui.components.MovaBottomNavigation
import dm.sample.mova.ui.showkase.getBrowserIntent
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.viewmodel.ThemeViewModel
import com.airbnb.android.showkase.models.Showkase
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ComposeApp(
    themeViewModel: ThemeViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    val paddingValues = WindowInsets.navigationBars.asPaddingValues()
    var bottomBarVisible by remember { mutableStateOf(true) }
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

    val scaffoldState = rememberScaffoldState()
    val snackBarCoroutineScope = rememberCoroutineScope()
    var snackBarVisible by remember { mutableStateOf(false) }

    navController.addOnDestinationChangedListener { _, _, _ -> bottomBarVisible = true }
    MovaTheme(isDarkTheme) {
        Scaffold(
            scaffoldState = scaffoldState,
            bottomBar = {
                MovaBottomNavigation(navController, bottomBarVisible)
            },
        ) {
            Surface(Modifier.padding(paddingValues)) {

                SetupNavGraph(
                    navController = navController,
                    startDestination = Screen.Splash.route,
                    onChangeBottomBarVisibility = { bottomBarVisible = it },
                    onShowSnackBar = { visibility, text ->
                        snackBarVisible = visibility
                        if (snackBarVisible) {
                            snackBarCoroutineScope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(text)
                            }
                        }
                    },
                    openShowkaseBrowser = {
                        if (BuildConfig.DEBUG) {
                            val intent = Showkase.getBrowserIntent(context)
                            context.startActivity(intent)
                        }
                    }
                )
            }

        }
    }

}