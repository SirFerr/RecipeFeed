@file:OptIn(ExperimentalEncodingApi::class)

package com.example.recipefeed.feature.composable.cards

import android.graphics.BitmapFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.recipefeed.R
import com.example.recipefeed.data.models.Recipe
import com.example.recipefeed.feature.composable.ShimmerEffect
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@Preview(showBackground = true)
@Composable
fun PreviewListItemCard() {
    ListItemCard(
        recipe = Recipe(
            id = 1,
            userId = 1,
            name = "Sample Recipe",
            addDate = java.util.Date(),
            isOnApprove = false,
            rejectReason = "",
            description = "",
            imageData = null,
            likes = 1,
            steps = null
        )
    )
}

@Composable
fun ListItemCard(
    recipe: Recipe,
    icon: ImageVector? = Icons.Filled.Favorite,
    isOnApprove: Boolean = recipe.isOnApprove,
    reason: String = recipe.rejectReason ?: "",
    onRecipeClick: () -> Unit = {},
    onEditClick: () -> Unit = {}
) {
    var showShimmer by remember { mutableStateOf(true) }

    Card(onClick = { onRecipeClick() }) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.main_padding)),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.sub_padding))
        ) {
            val imageBytes = recipe.imageData?.let { Base64.decode(it) }
            val image by remember {
                mutableStateOf(imageBytes?.let {
                    BitmapFactory.decodeByteArray(
                        it,
                        0,
                        it.size
                    )
                })
            }

            SubcomposeAsyncImage(
                model = image,
                contentDescription = recipe.name,
                modifier = Modifier
                    .height(200.dp)
                    .background(ShimmerEffect(showShimmer))
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner))),
                contentScale = ContentScale.Crop,
                onLoading = { showShimmer = true },
                onSuccess = { showShimmer = false }
            )
            Text(text = recipe.name, style = MaterialTheme.typography.titleLarge)

            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // В модели Recipe нет поля recipeLikes, поэтому убираем отображение лайков
                Text(
                    text = "ID: ${recipe.id}", // Пример замены, так как recipeLikes отсутствует
                    style = MaterialTheme.typography.bodyMedium
                )
                if (isOnApprove) {
                    Icon(Icons.Filled.Check, contentDescription = "Recipe on approval")
                } else if (icon != null) {
                    IconButton(onClick = {
                        if (icon == Icons.Filled.Edit) {
                            onEditClick()
                        }
                    }) {
                        Icon(imageVector = icon, contentDescription = "Action icon")
                    }
                }
            }
            if (reason.isNotEmpty()) {
                Spacer(Modifier.size(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.Info,
                        contentDescription = "Rejection reason",
                        tint = MaterialTheme.colorScheme.error
                    )
                    Spacer(Modifier.size(12.dp))
                    Text(text = reason, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
