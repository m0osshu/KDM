package com.example.myautoo.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myautoo.data.repository.UserPhotoRepository

class UserPhotoViewModelFactory(
    private val repository: UserPhotoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserPhotoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserPhotoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}