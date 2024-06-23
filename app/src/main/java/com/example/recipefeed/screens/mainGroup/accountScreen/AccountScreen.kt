package com.example.recipefeed.view.mainMenu.accountScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.recipefeed.R
import com.example.recipefeed.screens.Destinations


@Composable
fun AccountScreen(
    navController: NavHostController,
    firstNavController: NavHostController,
    viewModel: AccountScreenViewModel = hiltViewModel(),
) {

    val personalListItems = listOf(
        AccountScreenCards("Create recipe", Destinations.NEW_RECIPE, navController),
        AccountScreenCards("Show created recipes", Destinations.ADDED_RECIPES, navController),
    )

    val appListItems = listOf(
        AccountScreenCards(
            "Settings",
            Destinations.SETTINGS,
            navController
        ),
        AccountScreenCards("LogOut", Destinations.LOGIN_GROUP, firstNavController)
    )

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.main_padding))
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding)),
        ) {

            item {

            }
            item {
                CategoryText(text = "Personal")
            }
            items(personalListItems) {
                AccountListCard(accountScreenCards = it, viewModel)
            }

            item {
                CategoryText(text = stringResource(id = R.string.app_name))
            }
            items(appListItems) {
                AccountListCard(accountScreenCards = it, viewModel)
            }
        }
    }
}

@Composable
fun CategoryText(text: String) {
    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
    ) {
        Spacer(modifier = Modifier)
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.main_padding))
        )
        HorizontalDivider()

    }

}

@Composable
fun AccountListCard(accountScreenCards: AccountScreenCards, viewModel: AccountScreenViewModel) {
    var isLogOut by remember {
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (accountScreenCards.route == Destinations.LOGIN_GROUP) {
                    isLogOut = true
                } else
                    accountScreenCards.navController?.navigate(accountScreenCards.route)

            },
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner)),
    ) {
        Column(Modifier.padding(dimensionResource(id = R.dimen.main_padding))) {
            Text(text = accountScreenCards.title, style = MaterialTheme.typography.titleMedium)
        }
    }
    if (isLogOut) {
        AlertDialog(
            onDismissRequest = {
                isLogOut = false
            },
            dismissButton = {
                TextButton(onClick = {
                    isLogOut = false
                }) {
                    Text(text = "dismiss")
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    isLogOut = false
                    viewModel.deleteToken()
                    accountScreenCards.navController?.navigate(accountScreenCards.route) {
                        popUpTo("main") { inclusive = true }
                    }
                }) {
                    Text(text = "confirm")
                }
            }, text = {
                Text(text = "Are you sure you want to logOut?")
            })

    }

}