package com.example.recipefeed.ui.view.screen.mainMenu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.recipefeed.R
import com.example.recipefeed.data.recipe.model.Recipes
import com.example.recipefeed.ui.viewModel.RecipeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun searchScreen(
    navController: NavHostController? = null,
    recipeViewModel: RecipeViewModel = hiltViewModel()
) {
    val recipes by recipeViewModel.recipes.collectAsState()
    var text by remember {
        mutableStateOf("")
    }
    Column(
        Modifier
            .padding(dimensionResource(id = R.dimen.mainPadding))
            .fillMaxSize()

    ) {


        LazyColumn {
            item {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = text,
                    onValueChange = { text = it },
                    label = { Text(stringResource(id = R.string.search_title)) }
                )
                Spacer(Modifier.padding(dimensionResource(id = R.dimen.subPadding)))
            }

            items(recipes){
                listItem(it,navController)
                Spacer(Modifier.padding(dimensionResource(id = R.dimen.subPadding)))
            }

        }
    }
}