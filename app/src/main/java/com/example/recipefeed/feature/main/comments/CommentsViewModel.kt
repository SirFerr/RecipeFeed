package com.example.recipefeed.feature.main.comments

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.local.RoleSharedPreferencesManager
import com.example.recipefeed.data.models.Comment
import com.example.recipefeed.data.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val repository: RecipeRepository,
    private val roleSharedPreferencesManager: RoleSharedPreferencesManager
) : ViewModel() {

    private var _comments = mutableStateOf<List<Comment>>(emptyList())
    val comments: State<List<Comment>> = _comments

    private var _isModerator = mutableStateOf(false)
    val isModerator: State<Boolean> = _isModerator

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _isSuccessful = mutableStateOf(true)
    val isSuccessful: State<Boolean> = _isSuccessful

    init {
        _isModerator.value = roleSharedPreferencesManager.isModerator()
    }

    fun deleteComment(id: Int, reason: String) {
        if (_isModerator.value) {
            viewModelScope.launch {
                try {
                    val result = repository.rejectComment(id, reason)
                    if (result.isSuccess) {
                        // Обновляем список комментариев, исключая отклоненный
                        _comments.value = _comments.value.filter { it.id != id }
                    }
                } catch (e: Exception) {
                    // Можно добавить обработку ошибки, например, уведомление пользователя
                }
            }
        }
    }

    fun fetchComments(recipeId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = repository.getRecipeComments(
                    recipeId,
                    skip = 0,
                    limit = 100
                ) // Можно настроить skip и limit
                _isSuccessful.value = result.isSuccess
                if (result.isSuccess) {
                    _comments.value = result.getOrNull() ?: emptyList()
                }
            } catch (e: Exception) {
                _isSuccessful.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
}