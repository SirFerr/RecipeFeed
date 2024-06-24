package com.example.recipefeed.screens.mainGroup.scaffold.bottom

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.recipefeed.R
import com.example.recipefeed.screens.navigation.Destinations

@Composable
fun BottomNavigation(navController: NavController) {

    val list = listOf(
        NavBarItem(
            stringResource(id = R.string.home_title),
            Destinations.MainGroup.Main.route,
            Icons.Filled.Home
        ),
        NavBarItem(
            stringResource(id = R.string.search_title),
            Destinations.MainGroup.Search.route,
            Icons.Filled.Search
        ),
        NavBarItem(
            stringResource(id = R.string.favorite_title),
            Destinations.MainGroup.Favorite.route,
            Icons.Filled.Star
        ),
        NavBarItem(
            stringResource(id = R.string.account_title),
            Destinations.MainGroup.Account.route,
            Icons.Filled.AccountCircle
        ),
    )

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    NavigationBar(
        modifier = Modifier.wrapContentSize()
    ) {
        list.forEach {
            NavigationBarItem(
                modifier = Modifier,
                selected = currentRoute == it.route,
                onClick = {
                    navController.navigate(it.route) {


                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        restoreState = true
                    }
                },
//                label = { Text(it.screenName) },
//                alwaysShowLabel = false,
                icon = {
                    Icon(
                        it.icon, contentDescription = it.screenName
                    )
                }
            )
        }
    }

}