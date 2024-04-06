package com.example.recipefeed.view.subComponent

import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun updateBox(isLoading: Boolean, exec: () -> Unit, screen: @Composable () -> Unit) {
    val refreshState =
        rememberPullRefreshState(refreshing = isLoading, onRefresh = { exec() })
    Box(
        modifier = Modifier.pullRefresh(state = refreshState),
        contentAlignment = Alignment.TopCenter
    ) {
        screen()

        PullRefreshIndicator(refreshing = isLoading, state = refreshState)
    }
}