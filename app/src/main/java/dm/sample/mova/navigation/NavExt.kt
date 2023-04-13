package dm.sample.mova.navigation

import androidx.navigation.NavController

fun NavController.newRootScreen(screen: Screen) {
    newRootScreen(screen.route)
}

fun NavController.newRootScreen(screen: String) {
    if (screen == currentDestination?.route) return
    navigate(screen) {
        popUpTo(0)
    }
}

fun NavController.navigateTo(screen: Screen, navigateUp: Boolean = false) {
    navigateTo(screen.route, navigateUp = navigateUp)
}


fun NavController.navigateTo(route: String,  navigateUp: Boolean = false) {
    if (route == currentDestination?.route) return
    navigate(route) {
        if (navigateUp) navigateUp()
    }
}


fun NavController.popBackStackAllInstances(destination: String, inclusive: Boolean) {
    var popped: Boolean
    while (true) {
        popped = popBackStack(destination, inclusive)
        if (!popped) {
            break
        }
    }
}