package com.example.recipefeed.feature.main.comments

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.local.RoleSharedPreferencesManager
import com.example.recipefeed.data.models.Comment
import com.example.recipefeed.data.models.CommentCreate
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

    private var _currentUserId = mutableStateOf<Int?>(null)
    val currentUserId: State<Int?> = _currentUserId

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _isSuccessful = mutableStateOf(true)
    val isSuccessful: State<Boolean> = _isSuccessful

    init {
        _isModerator.value = roleSharedPreferencesManager.isModerator()
        fetchCurrentUser()
    }

    private fun fetchCurrentUser() {
        viewModelScope.launch {
            try {
                val result = repository.getCurrentUser()
                if (result.isSuccess) {
                    _currentUserId.value = result.getOrNull()?.id
                }
            } catch (e: Exception) {
                // Handle error if needed
            }
        }
    }

    fun createComment(recipeId: Int, text: String) {
        viewModelScope.launch {
            try {
                val commentCreate = CommentCreate(commentText = text)
                val result = repository.createComment(recipeId, commentCreate)
                if (result.isSuccess) {
                    result.getOrNull()?.let { newComment ->
                        _comments.value = listOf(newComment) + _comments.value
                    }
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun deleteComment(id: Int, reason: String = "") {
        viewModelScope.launch {
            try {
                val comment = _comments.value.find { it.id == id }
                val isAuthor = comment?.userId == _currentUserId.value

                if (_isModerator.value || isAuthor) {
                    val result = if (_isModerator.value && reason.isNotEmpty()) {
                        repository.rejectComment(id, reason)
                    } else {
                        repository.deleteComment(id)
                    }

                    if (result.isSuccess) {
                        _comments.value = _comments.value.filter { it.id != id }
                    }
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun fetchComments(recipeId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = repository.getRecipeComments(recipeId, skip = 0, limit = 100)
                _isSuccessful.value = result.isSuccess
                if (result.isSuccess) {
                    val comments = result.getOrNull() ?: emptyList()
                    // Fetch usernames for each comment
                    val commentsWithUsernames = comments.map { comment ->
                        val userResult = repository.getUserById(comment.userId)
                        val username = userResult.getOrNull()?.username ?: "User ${comment.userId}"
                        comment.copy(username = username)
                    }
                    _comments.value = commentsWithUsernames
                }
            } catch (e: Exception) {
                _isSuccessful.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
}