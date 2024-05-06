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
import androidx.navigation.NavHostController
import com.example.recipefeed.R
import com.example.recipefeed.utils.Destinations


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AccountScreen(
    navController: NavHostController? = null,
    firstNavController: NavHostController? = null
) {

    val list = listOf(
        AccountScreenCards("Create recipe", Destinations.newRecipe, navController),
        AccountScreenCards("Show created recipes", Destinations.addedRecipes, navController),
        AccountScreenCards("LogOut", Destinations.loginGroup, firstNavController)
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
                AccountListCard(accountScreenCards = it)
            }
        }
    }
}

