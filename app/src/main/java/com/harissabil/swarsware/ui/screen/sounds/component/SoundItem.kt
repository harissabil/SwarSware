package com.harissabil.swarsware.ui.screen.sounds.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.harissabil.swarsware.domain.model.Priority
import com.harissabil.swarsware.domain.model.Sound
import com.harissabil.swarsware.ui.theme.SwarSwareTheme
import com.harissabil.swarsware.ui.theme.spacing

@Composable
fun SoundItem(
    modifier: Modifier = Modifier,
    sound: Sound,
    onClick: () -> Unit,
) {
    OutlinedCard(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = sound.name,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
            Text(
                text = sound.description,
                fontSize = 14.sp,
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SoundItemPreview() {
    SwarSwareTheme {
        Surface {
            SoundItem(
                modifier = Modifier.padding(MaterialTheme.spacing.medium),
                sound = Sound(
                    id = 0L,
                    name = "Sound Name",
                    description = "Lorem Ipsum Lorem Ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum.",
                    priority = Priority.HIGH
                ),
                onClick = {}
            )
        }
    }
}