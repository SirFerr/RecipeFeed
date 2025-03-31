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
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipefeed.R
import com.example.recipefeed.feature.composable.CategoryText
import com.example.recipefeed.feature.composable.cards.AccountListCard
import com.example.recipefeed.feature.composable.cards.AccountScreenCards

@Composable
fun AccountScreen(
    viewModel: AccountScreenViewModel = hiltViewModel(),
    onCreateRecipe: () -> Unit,
    onShowCreatedRecipes: () -> Unit,
    onSettings: () -> Unit,
    onLogout: () -> Unit,
) {

    val personalListItems = listOf(
        AccountScreenCards("Create recipe", onCreateRecipe),
        AccountScreenCards("Show created recipes", onShowCreatedRecipes),
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
                AccountListCard(title = it.title, onClick = {
                    it.onClick()
                })
            }
            item {
                CategoryText(text = stringResource(id = R.string.app_name))
            }
            item {
                AccountListCard(title = "Settings", onClick = {
                    onSettings()
                })
            }
            item {
                AccountListCard(title = "LogOut", onClick = {
                    viewModel.deleteToken()
                    onLogout()
                })
            }

        }
    }
}



