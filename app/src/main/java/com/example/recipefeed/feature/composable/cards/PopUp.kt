package com.example.recipefeed.feature.composable.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup

@Composable
fun PickerPopup(
    anchorCoordinates: LayoutCoordinates,
    onDismiss: () -> Unit,
    onItemSelected: (String) -> Unit,
    current: String,
    list: List<String>,
) {
    val listState = rememberLazyListState()
    LaunchedEffect(current) {
        val currentIndex = list.indexOfFirst { it == current }
        if (currentIndex != -1) {
            listState.scrollToItem(
                index = currentIndex,
                scrollOffset = -50
            )
        } else if (list.isNotEmpty()) {
            listState.scrollToItem(0)
        }
    }
    val density = LocalDensity.current
    val targetSize = anchorCoordinates.size
    Popup(
        onDismissRequest = onDismiss,
        offset = with(density) {
            IntOffset(
                x = 0,
                y = (targetSize.height + 8.dp.toPx()
                    .toInt())
            )
        },
        alignment = Alignment.TopStart
    ) {
        Card(
            modifier = Modifier
                .width((with(density) { targetSize.width.toDp() }))
                .height(216.dp)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier.padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(list) {
                    Box(contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(43.dp)
                            .background(if (it == current) MaterialTheme.colorScheme.onPrimary else Color.Transparent)
                            .clickable { onItemSelected(it) }
                    ) {
                        Text(
                            text = it,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    PaddingValues(
                                        top = 8.dp, bottom = 8.dp, start = 12.dp
                                    )
                                ),

                            )
                    }

                }
            }
        }
    }
}