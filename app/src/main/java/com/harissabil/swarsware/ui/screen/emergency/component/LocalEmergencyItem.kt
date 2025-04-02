package com.harissabil.swarsware.ui.screen.emergency.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.harissabil.swarsware.ui.theme.SwarSwareTheme
import com.harissabil.swarsware.ui.theme.spacing

@Composable
fun LocalEmergencyItem(
    modifier: Modifier = Modifier,
    photoUrl: String,
    name: String,
    description: String,
    onCallClick: () -> Unit,
) {
    OutlinedCard(
        modifier = modifier.fillMaxWidth(),
        onClick = {}
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape),
                model = photoUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            IconButton(
                onClick = onCallClick,
                colors = IconButtonDefaults.iconButtonColors().copy(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Phone,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun LocalEmergencyItemPreview() {
    SwarSwareTheme {
        Surface {
            LocalEmergencyItem(
                modifier = Modifier.padding(12.dp),
                photoUrl = "https://static.promediateknologi.id/crop/0x0:0x0/0x0/webp/photo/p2/71/2024/05/15/4-900669986.jpg",
                name = "Damkar",
                description = "Pemadam Kebakaran",
                onCallClick = {}
            )
        }
    }
}