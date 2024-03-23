package com.example.recipefeed.ui.view.screens.mainMenu.list

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.recipefeed.R
import com.example.recipefeed.data.recipe.model.recipe.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


@OptIn(ExperimentalEncodingApi::class)
@Composable
fun listItem(
    recipe: Recipe,
    navController: NavController? = null,
    icon: ImageVector = Icons.Filled.Favorite
) {
    val imageBytes = Base64.decode(recipe.imageData)
    var image by rememberSaveable { mutableStateOf<Bitmap?>(null) }
    val _icon by remember { mutableStateOf(icon) }

    LaunchedEffect(imageBytes) {
        image = withContext(Dispatchers.IO) {
            BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        }
    }

    Card(Modifier.clickable { navController?.navigate("recipeScreen/${recipe.id}") }) {
        Row(
            Modifier
                .padding(dimensionResource(id = R.dimen.subPadding))
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.subPadding))
        ) {
            AsyncImage(
                model = image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.roundedCorner))),
                contentScale = ContentScale.Crop
            )
            Column(
                Modifier
                    .weight(2.5f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = recipe.recipeName, style =  MaterialTheme.typography.titleMedium)
                Text(text = recipe.recipeRating.toString(), style =  MaterialTheme.typography.bodyMedium)
            }
            IconButton(modifier = Modifier
                .weight(1f)
                .aspectRatio(1f), onClick = {
                if (_icon != Icons.Filled.Favorite && _icon != Icons.Filled.FavoriteBorder) {
                    navController?.navigate("editRecipeScreen/${recipe.id}")
                }
            }) {
                Icon(imageVector = _icon, contentDescription = null)
            }
        }
    }

}
