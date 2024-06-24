package com.example.recipefeed.screens.mainGroup

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.recipefeed.R

@Composable
fun TagItem(string: String, onClick:()->Unit) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier
            .wrapContentWidth()
    ) {
        Text(
            text = string,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.main_padding)),
            maxLines = 1

        )
    }
}