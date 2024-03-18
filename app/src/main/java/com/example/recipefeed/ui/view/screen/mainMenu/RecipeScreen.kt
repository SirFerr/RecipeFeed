package com.example.recipefeed.ui.view.screen.mainMenu

import android.graphics.Paint.Align
import android.icu.text.CaseMap.Title
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.recipefeed.R
import com.example.recipefeed.data.recipe.model.Recipe
import com.example.recipefeed.ui.view.screen.logInAndSignUp.spacer

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun recipeScreen(navController: NavHostController? = null, id: Int = -1) {
    val recipe = Recipe()
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(dimensionResource(id = R.dimen.mainPadding)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = recipe.recipeName, fontSize = 30.sp, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.subPadding)))
        AsyncImage(
            model = "",
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(10.dp)
                ).background(Color.White),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.subPadding)))
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
            Text(text = recipe.description)
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.subPadding)))
            Text(text = "Time to cook: " + recipe.timeToCook)
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.subPadding)))
            Text(text = "Ingridients: " + recipe.ingredients)
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.subPadding)))
            Text(text = "Recipe: " + recipe.ingredients)

        }

    }

}