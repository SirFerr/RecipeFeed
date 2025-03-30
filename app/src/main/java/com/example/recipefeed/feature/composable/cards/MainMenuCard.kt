@file:OptIn(ExperimentalEncodingApi::class)

package com.example.recipefeed.feature.composable.cards

import android.graphics.BitmapFactory
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import coil.compose.SubcomposeAsyncImage
import com.example.recipefeed.R
import com.example.recipefeed.data.models.Recipe
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@Composable
fun MainScreenCard(
    recipe: Recipe,
    onRecipeClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.main_padding))
            .fillMaxSize(0.95f)
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner)))
            .clickable { onRecipeClick() }
    ) {
        val imageBytes = recipe.imageData?.let { Base64.decode(it) }
        val image = imageBytes?.let { BitmapFactory.decodeByteArray(it, 0, it.size) }

        var isOpened by remember { mutableStateOf(false) }

        LaunchedEffect(key1 = recipe) {
            isOpened = false
        }

        SubcomposeAsyncImage(
            model = image,
            contentDescription = recipe.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            loading = { }
        )

        Card(
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner)),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .align(Alignment.BottomCenter)
                .padding(dimensionResource(id = R.dimen.main_padding))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .padding(dimensionResource(id = R.dimen.main_padding)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = recipe.name,
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center
                    )
                    IconButton(
                        modifier = Modifier.wrapContentSize(),
                        onClick = { isOpened = !isOpened }
                    ) {
                        Icon(
                            imageVector = if (isOpened) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                            contentDescription = "Expand or collapse description"
                        )
                    }
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.onSurfaceVariant)

                if (isOpened && recipe.description != null) {
                    Text(
                        text = recipe.description,
                        modifier = Modifier.fillMaxWidth()
                    )
                    HorizontalDivider(color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}