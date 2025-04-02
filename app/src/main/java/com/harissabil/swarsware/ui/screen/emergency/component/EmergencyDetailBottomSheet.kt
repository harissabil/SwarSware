package com.harissabil.swarsware.ui.screen.emergency.component

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.harissabil.swarsware.common.component.LargeDropdownMenu
import com.harissabil.swarsware.domain.model.Emergency
import com.harissabil.swarsware.domain.model.Sound
import com.harissabil.swarsware.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencyDetailBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    id: Long,
    name: String,
    imagePath: String,
    imageBitmap: Bitmap?,
    phoneNumber: String,
    soundList: List<Sound>,
    sound: Sound,
    onNameChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onSoundChange: (String) -> Unit,
    onImageClick: () -> Unit,
    sheetState: SheetState,
    onSaveEditClick: (Emergency) -> Unit,
    onDeleteClick: (Emergency) -> Unit,
) {
    var isNameError by remember { mutableStateOf(false) }
    var isPhoneNumberError by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.large)
                .padding(vertical = MaterialTheme.spacing.large),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.large)
                    .clip(CircleShape)
                    .aspectRatio(1f)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable { onImageClick() },
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = imageBitmap ?: imagePath,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.surfaceVariant,
                        ),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            ) {
                Text(
                    modifier = Modifier.alpha(0.5f),
                    text = "Name",
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth().takeIf { isNameError }?.then(
                        Modifier.border(
                            1.dp,
                            MaterialTheme.colorScheme.error,
                        )
                    ) ?: Modifier.fillMaxWidth(),
                    value = name,
                    onValueChange = onNameChange,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )
                if (isNameError) {
                    Text(
                        text = "Please enter a valid name.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            ) {
                Text(
                    modifier = Modifier.alpha(0.5f),
                    text = "Phone Number",
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth().takeIf { isPhoneNumberError }?.then(
                        Modifier.border(
                            1.dp,
                            MaterialTheme.colorScheme.error,
                        )
                    ) ?: Modifier.fillMaxWidth(),
                    value = phoneNumber,
                    onValueChange = onPhoneNumberChange,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    prefix = { Text(text = "+62") }
                )
                if (isPhoneNumberError) {
                    Text(
                        text = "Please enter a valid phone number.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            ) {
                Text(
                    modifier = Modifier.alpha(0.5f),
                    text = "Category",
                )
                LargeDropdownMenu(
                    modifier = Modifier.fillMaxWidth(),
                    items = soundList.map { it.name },
                    selectedIndex = soundList.indexOf(sound),
                    onItemSelected = { _, sound ->
                        onSoundChange(sound)
                    }
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    // Validate input fields
                    val isNameValid = name.isNotBlank()
                    val isPhoneValid =
                        phoneNumber.isNotBlank() && phoneNumber.length >= 8 && phoneNumber.all { it.isDigit() }

                    isNameError = !isNameValid
                    isPhoneNumberError = !isPhoneValid

                    // Only proceed if all fields are valid
                    if (isNameValid && isPhoneValid) {
                        onSaveEditClick(Emergency(id, name, phoneNumber, sound, imagePath))
                    }
                },
            ) {
                Text(text = "Save")
            }
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onDeleteClick(Emergency(id, name, phoneNumber, sound, imagePath)) },
                colors = ButtonDefaults.outlinedButtonColors().copy(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text(text = "Delete")
            }
        }
    }
}