package com.example.yourinteresests.android.ui.screens.searchscreen.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coffesbarcompose.modifier.shimmerBackground
import com.example.yourinteresests.android.theme.YourInterestTheme


@Composable
fun RowInformationShimmer(modifier: Modifier = Modifier) {

    Box(
        modifier = modifier
            .height(60.dp)
            .fillMaxWidth()
            .shimmerBackground(RoundedCornerShape(5.dp))
    )
}


@Composable
@Preview
fun RowInformationShimmerPreview() {
    YourInterestTheme {
        RowInformationShimmer()
    }

}