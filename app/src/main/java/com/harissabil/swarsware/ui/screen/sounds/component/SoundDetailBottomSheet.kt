package com.harissabil.swarsware.ui.screen.sounds.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.harissabil.swarsware.common.component.LargeDropdownMenu
import com.harissabil.swarsware.domain.model.Priority
import com.harissabil.swarsware.domain.model.Sound
import com.harissabil.swarsware.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoundDetailBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    sound: Sound,
    selectedPriority: Priority?,
    onPrioritySelected: (Priority?) -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.large)
                .padding(bottom = MaterialTheme.spacing.large),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = sound.name,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
            Text(
                text = sound.description,
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            val priorities = listOf("High", "Medium", "Low", "None")
            LargeDropdownMenu(
                modifier = Modifier.fillMaxWidth(),
                items = priorities,
                selectedIndex = priorities.indexOf(selectedPriority?.displayName ?: "None"),
                onItemSelected = { index, priority ->
                    onPrioritySelected(if (priority == "None") null else Priority.entries.find { it.displayName == priority })
                }
            )
        }
    }
}