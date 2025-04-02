package com.harissabil.swarsware.ui.screen.emergency

import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.harissabil.swarsware.common.component.CommonTopBar
import com.harissabil.swarsware.common.util.makePhoneCall
import com.harissabil.swarsware.ui.screen.emergency.component.AddEmergencyBottomSheet
import com.harissabil.swarsware.ui.screen.emergency.component.EmergencyDetailBottomSheet
import com.harissabil.swarsware.ui.screen.emergency.component.EmergencyItem
import com.harissabil.swarsware.ui.screen.emergency.component.LocalEmergencyItem
import com.harissabil.swarsware.ui.theme.spacing
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencyScreen(
    modifier: Modifier = Modifier,
    viewModel: EmergencyViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        canScroll = { false }
    )

    val emergencyDetailBottomSheetState = rememberModalBottomSheetState()
    var showEmergencyDetail by rememberSaveable { mutableStateOf(false) }

    val addEmergencyBottomSheetState = rememberModalBottomSheetState()
    var showAddEmergency by rememberSaveable { mutableStateOf(false) }

    val photoPicker =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                val bitmap = if (Build.VERSION.SDK_INT < 28) {
                    @Suppress("DEPRECATION")
                    MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, uri)
                    ImageDecoder.decodeBitmap(source)
                }
                viewModel.setPhotoBitmap(bitmap)
            }
        }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CommonTopBar(
                title = "Emergency",
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddEmergency = true },
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add new emergency"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(MaterialTheme.spacing.medium),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
        ) {
            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Local Emergency Services",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Start
                )
            }
            item {
                LocalEmergencyItem(
                    photoUrl = "https://static.promediateknologi.id/crop/0x0:0x0/0x0/webp/photo/p2/71/2024/05/15/4-900669986.jpg",
                    name = "Damkar",
                    description = "Pemadam Kebakaran",
                    onCallClick = {
                        // Currently not implemented
                    }
                )
            }
            if (state.emergencies.isNotEmpty()) {
                item {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Your List",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Start
                    )
                }
                items(items = state.emergencies, key = { it.id }) { emergency ->
                    EmergencyItem(
                        modifier = Modifier.animateItem(),
                        emergency = emergency,
                        onClick = {
                            showEmergencyDetail = true
                            viewModel.setEmergency(it)
                        },
                        onCallClick = { phoneNumber ->
                            makePhoneCall(context, phoneNumber)
                        }
                    )
                }
            }
        }

        if (showEmergencyDetail) {
            EmergencyDetailBottomSheet(
                sheetState = emergencyDetailBottomSheetState,
                onDismissRequest = {
                    showEmergencyDetail = false
                    viewModel.resetForm()
                },
                id = state.id,
                name = state.name,
                imagePath = state.photoPath!!,
                imageBitmap = state.photoBitmap,
                phoneNumber = state.phoneNumber,
                soundList = state.sounds,
                sound = state.sound ?: state.sounds.first(),
                onNameChange = viewModel::setName,
                onPhoneNumberChange = viewModel::setPhoneNumber,
                onSoundChange = viewModel::setSound,
                onImageClick = {
                    photoPicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                onSaveEditClick = {
                    viewModel.saveEmergency(it, context)
                    showEmergencyDetail = false
                },
                onDeleteClick = {
                    viewModel.deleteEmergency(it)
                    showEmergencyDetail = false
                },
            )
        }

        if (showAddEmergency) {
            AddEmergencyBottomSheet(
                sheetState = addEmergencyBottomSheetState,
                onDismissRequest = {
                    showAddEmergency = false
                    viewModel.resetForm()
                },
                id = state.id,
                name = state.name,
                imageBitmap = state.photoBitmap,
                phoneNumber = state.phoneNumber,
                soundList = state.sounds,
                sound = state.sound ?: state.sounds.first(),
                onNameChange = viewModel::setName,
                onPhoneNumberChange = viewModel::setPhoneNumber,
                onSoundChange = viewModel::setSound,
                onImageClick = {
                    photoPicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                onSaveEditClick = {
                    viewModel.saveEmergency(it, context)
                    showAddEmergency = false
                },
            )
        }
    }
}