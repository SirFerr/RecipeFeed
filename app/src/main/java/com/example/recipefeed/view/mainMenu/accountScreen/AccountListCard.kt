package com.example.recipefeed.view.mainMenu.accountScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
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
import com.example.recipefeed.R
import com.example.recipefeed.utils.Destinations

@Composable
fun AccountListCard(accountScreenCards: AccountScreenCards, viewModel: AccountScreenViewModel) {
    var isLogOut by remember {
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (accountScreenCards.route == Destinations.loginGroup) {
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