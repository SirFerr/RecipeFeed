package com.example.recipefeed.feature.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.recipefeed.R
import com.example.recipefeed.feature.composable.cards.TagItem

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagsGrid(list: List<String>, onClick: (String) -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        FlowRow(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = dimensionResource(id = R.dimen.main_padding)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
        ) {
            list.forEach {
                TagItem(string = it, onClick = {
                    onClick(it)
                })
            }
        }
        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.main_padding)))

    }


}