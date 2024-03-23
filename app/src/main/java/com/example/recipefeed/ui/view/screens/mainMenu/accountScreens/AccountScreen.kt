package com.example.recipefeed.ui.view.screens.mainMenu.accountScreens

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


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun accountScreen(
    navController: NavHostController? = null,
    firstNavController: NavHostController? = null
) {

    val list = listOf(
        AccountScreenCards("Create recipe", "newRecipeScreen", navController),
        AccountScreenCards("Show created recipes", "addedRecipesScreen", navController),
        AccountScreenCards("LogOut", "loginScreen", firstNavController)
    )

    Column(
        Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.mainPadding))
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.mainPadding)),

            ) {
            items(list) {
                accountListCard(accountScreenCards = it)
            }
        }
    }
}

