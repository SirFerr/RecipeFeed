package com.example.recipefeed.ui.view.screen.mainMenu

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.recipefeed.R
import com.example.recipefeed.data.model.Recipe


@Preview
@Composable
fun listItem(recipe: Recipe = Recipe(),navController: NavController ?= null) {
    var imageURL by remember {
        mutableStateOf("https://developer.android.com/static/codelabs/jetpack-compose-animation/img/jetpack_compose_logo_with_rocket.png")
    }
    var recipeName by remember {
        mutableStateOf(recipe.recipeName)
    }
    var description by remember {
        mutableStateOf(recipe.description)
    }
    var recipeRating by remember {
        mutableStateOf(recipe.recipeRating.toString())
    }
    Card(Modifier.clickable {
        navController?.navigate("recipeScreen/${recipe.id}")
    }) {
        Row(
            Modifier
                .padding(dimensionResource(id = R.dimen.subPadding))
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = rememberAsyncImagePainter(imageURL),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.subPadding)))
            Column(
                Modifier
                    .weight(2.5f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = recipeName, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.subPadding)))
            IconButton(modifier = Modifier
                .weight(1f)
                .aspectRatio(1f), onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Favorite, contentDescription = null)

            }
        }
    }
}