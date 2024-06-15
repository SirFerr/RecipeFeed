package com.example.recipefeed.mainMenu

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
import com.example.recipefeed.R

@Composable
fun TagItem(string: String) {
    Card(
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier
            .wrapContentWidth()
    ) {
        Text(
            text = string,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.main_padding) * 2),
            maxLines = 1

        )
    }
}