package com.example.recipefeed.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
fun stringToImage(string: String):Bitmap{
    val imageBytes = Base64.decode(string, 0)
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}