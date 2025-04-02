package com.harissabil.swarsware.ui.screen.emergency

import android.graphics.Bitmap
import com.harissabil.swarsware.domain.model.Emergency
import com.harissabil.swarsware.domain.model.Sound

data class EmergencyState(
    val emergencies: List<Emergency> = emptyList(),
    val sounds: List<Sound> = emptyList(),

    // add/edit
    val id: Long = 0L,
    val name: String = "",
    val phoneNumber: String = "",
    val sound: Sound? = null,
    val photoBitmap: Bitmap? = null,
    val photoPath: String? = null,
)
