package com.example.recipefeed.feature.mainGroup.comments

import androidx.lifecycle.ViewModel
import com.example.recipefeed.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val repository: Repository

) :
    ViewModel() {

}