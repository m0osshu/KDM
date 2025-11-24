package com.example.myautoo.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myautoo.data.repository.MarcaRepository

class MarcaAdminViewModelFactory(
    private val repository: MarcaRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MarcaAdminViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MarcaAdminViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}