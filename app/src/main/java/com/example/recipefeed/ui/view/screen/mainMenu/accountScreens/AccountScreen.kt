package com.example.recipefeed.ui.view.screen.mainMenu.accountScreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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

        list.forEach {
            accountListCard(it)
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.subPadding)))

        }
    }
}

@Composable
fun accountListCard(accountScreenCards: AccountScreenCards) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable {
//            if (accountScreenCards.route == "loginScreen")
//                accountScreenCards?.navController.
//            else
                accountScreenCards.navController?.navigate(accountScreenCards.route)
        }) {
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.mainPadding)))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.mainPadding)))
            Text(text = accountScreenCards.title)
        }
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.mainPadding)))

    }
}