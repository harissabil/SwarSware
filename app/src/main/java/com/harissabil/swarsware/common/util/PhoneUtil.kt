package com.harissabil.swarsware.common.util

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.net.toUri
import timber.log.Timber

fun makePhoneCall(context: Context, phoneNumber: String) {
    try {
        // Format number appropriately (remove any spaces, dashes, etc.)
        val cleanNumber = phoneNumber.replace(Regex("[^0-9+]"), "")
        val formattedNumber = if (cleanNumber.startsWith("+")) {
            cleanNumber
        } else if (cleanNumber.startsWith("0")) {
            "+62" + cleanNumber.substring(1) // Replace leading 0 with +62
        } else {
            "+62$cleanNumber" // Add country code with +
        }

        // Use ACTION_CALL to directly initiate the call without showing the dialer
        val callIntent = Intent(Intent.ACTION_CALL).apply {
            data = "tel:$formattedNumber".toUri()
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        context.startActivity(callIntent)
    } catch (e: Exception) {
        Timber.e(e, "Couldn't make phone call")
        Toast.makeText(
            context,
            "Couldn't make phone call. Check app permissions.",
            Toast.LENGTH_SHORT
        ).show()
    }
}