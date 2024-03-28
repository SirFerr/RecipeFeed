package com.example.recipefeed.ui.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.recipefeed.data.RetrofitObject
import com.example.recipefeed.data.recipe.model.recipe.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class OtherNetworkViewModel @Inject constructor() : ViewModel() {

    fun addRecipes(recipe: Recipe, imagePart: MultipartBody.Part?,context:Context) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = imagePart?.let { RetrofitObject.api.addRecipe(recipe, it) }
                if (response?.isSuccessful == true) {
                    Log.d("Successful", response.code().toString())
                    withContext(Dispatchers.Main){
                        Toast.makeText(context,"Successful",Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("err", response?.code().toString())
                    withContext(Dispatchers.Main){
                        Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()
                    }
                }

            } catch (e: Exception) {
                Log.e("err", e.toString())
            }
        }
    }
}