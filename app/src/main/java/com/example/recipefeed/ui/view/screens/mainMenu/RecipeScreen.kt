package com.example.recipefeed.ui.view.screens.mainMenu

import android.graphics.BitmapFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.recipefeed.R
import com.example.recipefeed.data.recipe.model.recipe.Recipe
import com.example.recipefeed.ui.viewModel.RecipeViewModel
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun recipeScreen(
    navController: NavHostController? = null,
    id: Int = -1,
    recipeViewModel: RecipeViewModel = hiltViewModel()
) {
    var recipe by remember { mutableStateOf<Recipe?>(null) }

    LaunchedEffect(true) {
        recipe = recipeViewModel.getById(id)

    }



    if (recipe != null) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(dimensionResource(id = R.dimen.main_padding)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(text = recipe!!.recipeName, style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.sub_padding)))

            AsyncImage(
                model = if (recipe != null) {
                    val imageBytes = Base64.decode(recipe!!.imageData)
                    BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                } else null, contentDescription = null, modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(10.dp)
                    )
                    .background(Color.White)
                    .wrapContentSize(), contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.sub_padding)))
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                Text(text = recipe!!.description, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.sub_padding)))
                Divider()
                Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.sub_padding)))

                Text(
                    text = stringResource(id = R.string.time_to_cook) + ": " + recipe!!.timeToCook,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.sub_padding)))
                Text(
                    text = stringResource(id = R.string.ingridients) + ": " + recipe!!.ingredients,
                    style = MaterialTheme.typography.bodyLarge
                )
//                Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.subPadding)))
//                Text(
//                    text = "Recipe: " + recipe!!.ingredients,
//                    style = MaterialTheme.typography.bodyLarge
//                )

            }

        }
    }

}