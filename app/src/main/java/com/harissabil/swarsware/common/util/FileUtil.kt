package com.harissabil.swarsware.common.util

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

fun saveImage(image: Bitmap, fileName: String, context: Context): String? {
    var savedImagePath: String? = null
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    var success = true
    if (storageDir != null) {
        if (!storageDir.exists()) {
            success = storageDir.mkdirs()
        }
    }
    if (success) {
        val imageFile = File(storageDir, "${fileName}.jpg")
        savedImagePath = imageFile.absolutePath

        if (imageFile.exists()) {
            imageFile.delete()
        }

        try {
            val fOut: OutputStream = FileOutputStream(imageFile)
            image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
            fOut.close()
        } catch (e: Exception) {
            e.printStackTrace()
            Timber.tag("IMAGE_PATH").e(e.toString())
        }
    }
    Timber.tag("IMAGE_PATH").d(savedImagePath.toString())
    return savedImagePath
}

fun deleteImage(imagePath: String) {
    val file = File(imagePath)
    if (file.exists()) {
        file.delete()
    }
}