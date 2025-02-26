package com.example.recipefeed.feature.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.recipefeed.R

@Composable
fun CategoryText(text: String) {
    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
    ) {
        Spacer(modifier = Modifier)
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.main_padding))
        )
        HorizontalDivider()

    }

}