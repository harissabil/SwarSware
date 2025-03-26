package com.harissabil.swarsware.ui.screen.onboarding.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harissabil.swarsware.ui.theme.SwarSwareTheme
import com.harissabil.swarsware.ui.theme.spacing

@Composable
fun OnboardingCard(
    title: String,
    description: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column {
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
            Text(
                modifier = Modifier
                    .animateContentSize()
                    .padding(horizontal = MaterialTheme.spacing.large),
                text = title,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            Text(
                modifier = Modifier
                    .animateContentSize()
                    .padding(horizontal = MaterialTheme.spacing.large),
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview
@Composable
private fun OnBoardingCardPreview() {
    SwarSwareTheme {
        OnboardingCard(
            title = "Title",
            description = "Description"
        )
    }
}