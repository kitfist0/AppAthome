package app.athome.core.interfaces

import androidx.navigation.NavController

abstract class BaseNavigator {

    var navController: NavController? = null

    fun bind(navController: NavController) {
        this.navController = navController
    }

    fun rebind(navController: NavController) {
        this.navController ?: apply { this.navController = navController }
    }

    fun unbind() {
        navController = null
    }
}
