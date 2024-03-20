@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.recipefeed.ui.view.screen.mainMenu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.recipefeed.R
import com.example.recipefeed.data.recipe.model.Recipe


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun favoriteScreen(navController: NavHostController? = null) {
    var text by remember {
        mutableStateOf("")
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.mainPadding))
    ) {


        LazyColumn {
            items(10) {
                listItem(navController = navController, recipe = Recipe(id = it))
                if(it!=9)
                    Spacer(Modifier.padding(dimensionResource(id = R.dimen.subPadding)))
            }
        }
    }
}