package com.example.recipefeed.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import kotlin.io.encoding.ExperimentalEncodingApi

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

@OptIn(ExperimentalEncodingApi::class)
fun base64ToBitmap(base64String: String?): Bitmap? {
    val imageBytes = base64String?.let { kotlin.io.encoding.Base64.decode(it) }
    return imageBytes?.let { BitmapFactory.decodeByteArray(it, 0, it.size) }
}

fun uriToFile(uri: Uri, context: Context): File {
    val file = File(context.cacheDir, "recipe_image_${System.currentTimeMillis()}.jpg")
    context.contentResolver.openInputStream(uri)?.use { input ->
        FileOutputStream(file).use { output ->
            input.copyTo(output)
        }
    }
    return file
}
