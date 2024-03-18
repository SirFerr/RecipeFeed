package com.example.recipefeed.ui.view.screen.mainMenu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.recipefeed.R


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun accountScreen(
    navController: NavHostController? = null,
    firstNavController: NavHostController? = null
) {

    val list = listOf(
        AccountScreenCards("Create recipe", ""),
        AccountScreenCards("Show created recipes", ""),
        AccountScreenCards("LogOut", "loginScreen")
    )

    Column(
        Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.mainPadding))
    ) {
        list.forEach {
            Card(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    firstNavController?.navigate(it.route)
                }) {
                Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.mainPadding)))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.mainPadding)))
                    Text(text = it.title)
                }
                Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.mainPadding)))

            }
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.subPadding)))

        }
    }
}