package com.harissabil.swarsware.ui.screen.home.component

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.harissabil.swarsware.common.util.toDdMmYyyyHhMmSs
import com.harissabil.swarsware.domain.model.History
import com.harissabil.swarsware.domain.model.Priority
import com.harissabil.swarsware.ui.theme.spacing

@Composable
fun HistoryItem(
    modifier: Modifier = Modifier,
    history: History,
    onDeleteClick: (History) -> Unit,
) {
    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var itemHeight by remember {
        mutableStateOf(0.dp)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val density = LocalDensity.current

    Box(
        modifier = modifier.onSizeChanged {
            itemHeight = with(density) { it.height.toDp() }
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .background(color = MaterialTheme.colorScheme.surfaceVariant)
                .indication(interactionSource, LocalIndication.current)
                .pointerInput(true) {
                    detectTapGestures(
                        onLongPress = {
                            isContextMenuVisible = true
                            pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                        },
                        onPress = {
                            val press = PressInteraction.Press(it)
                            interactionSource.emit(press)
                            tryAwaitRelease()
                            interactionSource.emit(PressInteraction.Release(press))
                        },
                        onTap = {
                            isContextMenuVisible = false
                        }
                    )
                },
            contentAlignment = Alignment.Center,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.medium),
            ) {
                Icon(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    imageVector = Icons.Default.History,
                    tint = MaterialTheme.colorScheme.secondary,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = history.sound.name,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = history.sound.priority?.displayName ?: "-",
                        style = MaterialTheme.typography.bodyLarge,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        color = when (history.sound.priority) {
                            Priority.HIGH -> MaterialTheme.colorScheme.error
                            Priority.MEDIUM -> MaterialTheme.colorScheme.error.copy(alpha = 0.75f)
                            Priority.LOW -> MaterialTheme.colorScheme.error.copy(alpha = 0.5f)
                            null -> MaterialTheme.colorScheme.onSurfaceVariant
                        },
                    )
                }
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
                Text(
                    text = history.timestamp.toDdMmYyyyHhMmSs(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.75f),
                )
            }

            DropdownMenu(
                expanded = isContextMenuVisible,
                onDismissRequest = {
                    isContextMenuVisible = false
                },
                offset = pressOffset.copy(
                    y = pressOffset.y - itemHeight
                ),
            ) {
                DropdownMenuItem(
                    onClick = {
                        onDeleteClick(history)
                        isContextMenuVisible = false
                    },
                    text = { Text(text = "Delete") },
                )
            }
        }
    }
}