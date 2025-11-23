package com.example.myautoo.ui.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myautoo.data.remote.dto.MarcaDto
import com.example.myautoo.data.repository.MarcaRepository
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {

    private val repo = MarcaRepository()

    private val _categories = mutableStateOf<List<MarcaDto>>(emptyList())
    val categories: State<List<MarcaDto>> = _categories

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    init {
        loadMarcas()
    }

    private fun loadMarcas() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _categories.value = repo.getMarcas()
            } catch (e: Exception) {
                _error.value = "Error cargando marcas: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
