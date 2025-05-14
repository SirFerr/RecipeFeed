package com.example.recipefeed.data.models

import com.example.recipefeed.data.api.Translator
import com.google.gson.annotations.SerializedName

data class Tag(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
) {
    suspend fun translateToRussian(): Tag {
        return this.copy(name = Translator.translate(name))
    }
}

data class TagCreate(
    @SerializedName("name") val name: String
) {
    suspend fun translateToRussian(): TagCreate {
        return this.copy(name = Translator.translate(name))
    }
}
