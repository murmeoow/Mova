package dm.sample.mova.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.navigation.Screen
import dm.sample.mova.navigation.popBackStackAllInstances
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.Grayscale500
import dm.sample.mova.ui.theme.bodyXSmallBold
import dm.sample.mova.ui.theme.bodyXSmallMedium
import com.airbnb.android.showkase.annotation.ShowkaseComposable

@Composable
fun MovaBottomNavigation(navController: NavController, isVisible: Boolean) {
    MovaBottomBarAnimation(isVisible) {
        val visibleRoutes = listOf(
            Screen.Home,
            Screen.Explore,
            Screen.MyList,
            Screen.Downloads,
            Screen.Profile,
        )

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        if (currentRoute in visibleRoutes.map { it.route }) {
            BottomNavigationContent(
                currentRoute = currentRoute,
                onSelect = { screenRoute ->
                    if (navController.currentDestination?.route != screenRoute) {
                        navController.navigate(screenRoute) {
                            navController.popBackStackAllInstances(screenRoute, true)
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun MovaBottomBarAnimation(isVisible: Boolean, bottomNavigation: @Composable () -> Unit) {

    val density = LocalDensity.current
    val enterAnimation = slideInVertically {
        with(density) { 80.dp.roundToPx() }

    } + expandVertically(
        expandFrom = Alignment.Bottom
    )
    val exitAnimation = slideOutVertically {
        with(density) { 80.dp.roundToPx() }
    }

    AnimatedVisibility(visible = isVisible, enter = enterAnimation, exit = exitAnimation) {
        bottomNavigation()
    }
}

@Composable
private fun BottomNavigationContent(currentRoute: String?, onSelect: (String) -> Unit) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .navigationBarsPadding()
            .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp))
    ) {

        BottomNavigationItemContent(
            onClick = {
                onSelect(Screen.Home.route)
            },
            isSelected = isHomeRoute(currentRoute),
            title = stringResource(R.string.nav_home),
            selectedIcon = painterResource(R.drawable.ic_home_red),
            unselectedIcon = painterResource(R.drawable.ic_home),
        )

        BottomNavigationItemContent(
            onClick = {
                onSelect(Screen.Explore.passArgument())
            },
            isSelected = currentRoute == Screen.Explore.route,
            title = stringResource(R.string.nav_explore),
            selectedIcon = painterResource(R.drawable.ic_explorer_red),
            unselectedIcon = painterResource(R.drawable.ic_explorer),
        )

        BottomNavigationItemContent(
            onClick = {
                onSelect(Screen.MyList.route)
            },
            isSelected = currentRoute == Screen.MyList.route,
            title = stringResource(R.string.nav_mylist),
            selectedIcon = painterResource(R.drawable.ic_mylist_red),
            unselectedIcon = painterResource(R.drawable.ic_mylist),
        )

        BottomNavigationItemContent(
            onClick = {
                onSelect(Screen.Downloads.route)
            },
            isSelected = currentRoute == Screen.Downloads.route,
            title = stringResource(R.string.nav_downloads),
            selectedIcon = painterResource(R.drawable.ic_download_red),
            unselectedIcon = painterResource(R.drawable.ic_download),
        )

        BottomNavigationItemContent(
            onClick = {
                onSelect(Screen.Profile.route)
            },
            isSelected = isProfileRoute(currentRoute),
            title = stringResource(R.string.nav_profile),
            selectedIcon = painterResource(R.drawable.ic_profile_red),
            unselectedIcon = painterResource(R.drawable.ic_profile),
        )

    }
}

private fun isProfileRoute(route: String?): Boolean {
    val profileIncludeScreens = listOf(
        Screen.Profile.route,
    )
    return profileIncludeScreens.contains(route)
}

private fun isHomeRoute(route: String?): Boolean {
    val homeIncludeScreens = listOf(
        Screen.Home.route,
    )
    return homeIncludeScreens.contains(route)
}

@Composable
private fun RowScope.BottomNavigationItemContent(
    onClick: () -> Unit,
    isSelected: Boolean,
    title: String,
    selectedIcon: Painter,
    unselectedIcon: Painter,
) {
    BottomNavigationItem(
        selected = isSelected,
        selectedContentColor = MaterialTheme.colors.primary,
        unselectedContentColor = Grayscale500,
        onClick = onClick,
        label = {
            Text(
                text = title,
                style = if (isSelected) bodyXSmallBold else bodyXSmallMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        },
        icon = {
            Icon(
                if (isSelected) selectedIcon else unselectedIcon,
                contentDescription = "Home navigation icon",
                modifier = Modifier.size(24.dp)
            )
        }
    )
}


@ShowkaseComposable(
    name = "Mova Bottom Navigation",
    group = Constants.SHOWKASE_GROUP_COMPONENTS,
)
@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewBottomNavigationContent() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        var selectedScreenRoute by remember {
            mutableStateOf(Screen.Home.route)
        }

        BottomNavigationContent(
            currentRoute = selectedScreenRoute,
            onSelect = {
                selectedScreenRoute = it
            },
        )

    }
}