package com.example.recipefeed.feature.composable.cards

import android.graphics.Bitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.recipefeed.R
import com.example.recipefeed.data.models.Recipe
import com.example.recipefeed.utils.base64ToBitmap
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalEncodingApi::class)
@Composable
fun ListItemCard(
    recipe: Recipe,
    onRecipeClick: () -> Unit,
    onEditClick: () -> Unit = {},
    onFavoriteClick: () -> Unit = {}, // Добавляем обработчик для избранного
    isEdit: Boolean = false,
    isFavorite: Boolean = false, // Состояние избранного
    showFavorite: Boolean = true,
    modifier: Modifier = Modifier
) {

    // Cache the Bitmap using remember to avoid recomputing it on every recomposition
    val bitmap: Bitmap? = remember(recipe.imageData) {
        recipe.imageData?.let { base64ToBitmap(it) }
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onRecipeClick() },
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner))
    ) {
        Row(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.main_padding)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
        ) {
            SubcomposeAsyncImage(
                model = bitmap,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner))),
                contentScale = ContentScale.Crop,
                loading = { CircularProgressIndicator() }
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.sub_padding))
            ) {
                Text(
                    text = recipe.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = recipe.description ?: "No description",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.sub_padding))
            ) {
                // Иконка избранного
                if (showFavorite)
                    IconButton(onClick = onFavoriteClick) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                            tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }

                if (isEdit) {
                    IconButton(onClick = onEditClick) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Edit"
                        )
                    }
                }
            }
        }
    }
}