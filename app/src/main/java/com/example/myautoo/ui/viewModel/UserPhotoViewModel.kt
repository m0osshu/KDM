package com.example.myautoo.ui.viewModel

import androidx.lifecycle.ViewModel
import com.example.myautoo.data.local.entity.UserPhotoEntity
import com.example.myautoo.data.repository.UserPhotoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserPhotoViewModel(private val repository: UserPhotoRepository) : ViewModel() {

    private val _photoUri = MutableStateFlow<String?>(null)
    val photoUri: StateFlow<String?> = _photoUri

    fun loadPhoto(userId: String) {
        viewModelScope.launch {
            val photo = repository.getPhoto(userId)
            _photoUri.value = photo?.photoUri
        }
    }

    fun savePhoto(userId: String, uri: String) {
        viewModelScope.launch {
            repository.savePhoto(UserPhotoEntity(userId = userId, photoUri = uri))
            _photoUri.value = uri
        }
    }

    fun deletePhoto(userId: String) {
        viewModelScope.launch {
            val photo = repository.getPhoto(userId)
            if (photo != null) {
                repository.deletePhoto(photo)
                _photoUri.value = null
            }
        }
    }
}