package com.example.recipefeed.ui.view.screens.mainMenu.accountScreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.recipefeed.R

@Composable
fun accountListCard(accountScreenCards: AccountScreenCards) {
    Card(modifier = Modifier

        .fillMaxWidth()
        .clickable {
            accountScreenCards.navController?.navigate(accountScreenCards.route)
        }) {
        Column(Modifier.padding(dimensionResource(id = R.dimen.mainPadding))) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = accountScreenCards.title)
            }
        }


    }
}