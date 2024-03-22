package com.example.recipefeed.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.core.net.toFile
import java.io.ByteArrayOutputStream

fun uriToBase64(context: Context,uri: Uri): String {
    // Read the image file into a Bitmap.
    val inputStream = context.contentResolver.openInputStream(uri)
    val bitmap = BitmapFactory.decodeStream(inputStream)

    // Convert the Bitmap to a byte array.
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()

    // Encode the byte array as a base64 string.
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}
