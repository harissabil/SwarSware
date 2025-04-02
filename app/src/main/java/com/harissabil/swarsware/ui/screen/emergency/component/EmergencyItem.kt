package com.harissabil.swarsware.ui.screen.emergency.component

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.harissabil.swarsware.domain.model.Emergency
import com.harissabil.swarsware.ui.theme.spacing

@Composable
fun EmergencyItem(
    modifier: Modifier = Modifier,
    emergency: Emergency,
    onClick: (emergency: Emergency) -> Unit,
    onCallClick: (phoneNumber: String) -> Unit,
) {
    OutlinedCard(
        modifier = modifier.fillMaxWidth(),
        onClick = { onClick(emergency) }
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
                model = emergency.photo,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = emergency.name,
                    style = MaterialTheme.typography.titleMedium,
                )
                Row {
                    Text(
                        text = "Auto Inform: ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = emergency.sound.name,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
            IconButton(
                onClick = { onCallClick(emergency.phoneNumber) },
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