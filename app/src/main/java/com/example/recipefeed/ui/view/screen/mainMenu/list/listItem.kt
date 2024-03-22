package com.example.recipefeed.ui.view.screen.mainMenu.list

import android.graphics.BitmapFactory
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.recipefeed.R
import com.example.recipefeed.data.recipe.model.Recipe
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


@OptIn(ExperimentalEncodingApi::class)
@Composable
fun listItem(
    recipe: Recipe,
    navController: NavController? = null,
    icon: ImageVector = Icons.Filled.Favorite
) {
    var imageURL by remember {
        mutableStateOf("https://developer.android.com/static/codelabs/jetpack-compose-animation/img/jetpack_compose_logo_with_rocket.png")
    }
    val imageBytes = Base64.decode(recipe.imageData)
    val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    val _icon by remember {
        mutableStateOf(icon)
    }


    Card(Modifier.clickable {
        navController?.navigate("recipeScreen/${recipe.id}")
    }) {


        Row(
            Modifier
                .padding(dimensionResource(id = R.dimen.subPadding))
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                model = image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .aspectRatio(1f)
                    .clip(
                        RoundedCornerShape(dimensionResource(id = R.dimen.roundedCorner))
                    ),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.subPadding)))
            Column(
                Modifier
                    .weight(2.5f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = recipe.recipeName, fontSize = 14.sp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = recipe.recipeRating.toString(), fontSize = 12.sp)
                }

            }
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.subPadding)))
            IconButton(modifier = Modifier
                .weight(1f)
                .aspectRatio(1f), onClick = {
                if (_icon == Icons.Filled.Favorite || _icon == Icons.Filled.FavoriteBorder) {
                } else{
                    navController?.navigate("editRecipeScreen/${recipe.id}")
                }

            }) {
                Icon(imageVector = _icon, contentDescription = null)

            }
        }
    }
}