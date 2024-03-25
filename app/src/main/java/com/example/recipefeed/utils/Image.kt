package com.example.recipefeed.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

fun convertToMultipart(image: Any?, context: Context): MultipartBody.Part? {
    return when (image) {
        is Bitmap -> {
            val byteArrayOutputStream = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val imageBytes = byteArrayOutputStream.toByteArray()
            val requestFile = imageBytes.toRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("image", "image.png", requestFile)
        }

        is Uri -> {
            val inputStream = context.contentResolver.openInputStream(image)
            val bytes = inputStream?.readBytes()
            bytes ?: return null
            val requestFile = bytes.toRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("image", "image.png", requestFile)
        }

        else -> null
    }
}
