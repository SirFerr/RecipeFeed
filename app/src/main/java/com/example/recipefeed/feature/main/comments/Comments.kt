package com.example.recipefeed.feature.main.comments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.recipefeed.R


@Composable
fun Comments(
    viewModel: CommentsViewModel = hiltViewModel(),
    navController: NavController = rememberNavController()
) {
    LazyColumn(
        state = rememberLazyListState(),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.main_padding)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding)),
    ) {

    }
}


