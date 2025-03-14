package com.example.recipefeed.feature.main.comments

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.recipefeed.data.Repository
import com.example.recipefeed.feature.composable.cards.Comment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val repository: Repository

) :
    ViewModel() {

    private var _comments = mutableStateOf<List<Comment>>(listOf())
    val comments: State<List<Comment>> = _comments

    private var _isModerator = mutableStateOf(false)
    val isModerator: State<Boolean> = _isModerator

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _isSuccessful = mutableStateOf(false)
    val isSuccessful: State<Boolean> = _isSuccessful

    fun deleteComment(id: Int, reason: String) {
        if (_isModerator.value) {

        } else {

        }
    }


    fun fetchComments(recipeId: Int) {
        _isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            try {
//                val response = repository.getFavouritesRecipes()
//                _isSuccessful.value = response.isSuccessful
//                if (response.isSuccessful) {
//
//                }
            } catch (e: Exception) {
                _isSuccessful.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
}