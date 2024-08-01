package com.example.yourinteresests.android.utils

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController


@Composable
fun BottomCustomNavigation(navHostController: NavHostController, navDestination: NavDestination) {

    BottomNavigation() {
        BottomScreens.screens().forEach {
            AddItem(
                navController = navHostController,
                screen = it,
                currentDestination = navDestination
            )
        }
    }

}


@Composable
fun RowScope.AddItem(
    navController: NavController,
    screen: BottomBarScreen,
    currentDestination: NavDestination
) {
    BottomNavigationItem(
        selected = currentDestination.hierarchy.any {
            it.route == screen.route
        },
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        icon = {
            Icon(
                modifier = Modifier.size(20.dp), contentDescription = "Icon Navigation",
                painter = painterResource(id = screen.icon)
            )
        },
        unselectedContentColor = MaterialTheme.colors.secondary.copy(0.3f),
        selectedContentColor = MaterialTheme.colors.secondary
    )
}