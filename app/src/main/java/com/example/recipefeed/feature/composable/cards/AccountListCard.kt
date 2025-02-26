package com.example.recipefeed.feature.composable.cards

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

data class AccountScreenCards(
    val title: String,
    val onClick: () -> Unit
)


@Composable
fun AccountListCard(title: String, isLogOut: Boolean = false, onClick: () -> Unit) {
    var isLogOutShow by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (isLogOut) isLogOutShow = true
                else
                    onClick()
            },
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner)),
    ) {
        Column(Modifier.padding(dimensionResource(id = R.dimen.main_padding))) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
        }
    }
    if (isLogOutShow) {
        AlertDialog(
            onDismissRequest = {
                isLogOutShow = false
            },
            dismissButton = {
                TextButton(onClick = {
                    isLogOutShow = false
                }) {
                    Text(text = "dismiss")
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    isLogOutShow = false
                    onClick()
                }) {
                    Text(text = "confirm")
                }
            }, text = {
                Text(text = "Are you sure you want to logOut?")
            })

    }

}