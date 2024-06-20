package com.example.recipefeed.view.mainMenu.accountScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.recipefeed.R
import com.example.recipefeed.utils.Destinations


@Composable
fun AccountScreen(
    navController: NavHostController,
    firstNavController: NavHostController,
    viewModel: AccountScreenViewModel = hiltViewModel(),
) {

    val list = listOf(
        AccountScreenCards("Create recipe", Destinations.NEW_RECIPE, navController),
        AccountScreenCards("Show created recipes", Destinations.ADDED_RECIPES, navController),
        AccountScreenCards("LogOut", Destinations.LOGIN_GROUP, firstNavController)
    )

    Column(
        Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.main_padding))
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding)),

            ) {
            items(list) {
                AccountListCard(accountScreenCards = it,viewModel)
            }
        }
    }
}


