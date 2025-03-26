package com.harissabil.swarsware.ui.screen.onboarding.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun OnboardingImage(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int,
) {
    Box(modifier = modifier) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.8f),
            painter = painterResource(image),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}