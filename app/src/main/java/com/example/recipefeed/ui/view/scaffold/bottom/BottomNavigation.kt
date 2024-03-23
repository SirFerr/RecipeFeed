package com.example.recipefeed.ui.view.scaffold.bottom

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.recipefeed.R

@Composable
fun bottomNavigation(navController: NavController) {
    val list = listOf(
        NavBarItem(
            stringResource(id = R.string.home_title),
            "mainScreen",
            Icons.Filled.Home
        ),
        NavBarItem(
            stringResource(id = R.string.search_title),
            "searchScreen",
            Icons.Filled.Search
        ),
        NavBarItem(
            stringResource(id = R.string.favorite_title),
            "favoriteScreen",
            Icons.Filled.Star
        ),
        NavBarItem(
            stringResource(id = R.string.account_title),
            "accountScreen",
            Icons.Filled.AccountCircle
        ),
    )

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    NavigationBar {
        list.forEach {
            NavigationBarItem(
                selected = currentRoute == it.route,
                onClick = { navController.navigate(it.route) },
                label = { Text(it.screenName,style =  MaterialTheme.typography.bodyMedium) },
                icon = { Icon(
                    it.icon, contentDescription = it.screenName
                ) })
        }
    }

}