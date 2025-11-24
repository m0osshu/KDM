package com.example.myautoo.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myautoo.data.remote.dto.MarcaDto
import com.example.myautoo.data.repository.MarcaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MarcaAdminViewModel(
    private val repository: MarcaRepository
) : ViewModel() {

    private val _marcas = MutableStateFlow<List<MarcaDto>>(emptyList())
    val marcas: StateFlow<List<MarcaDto>> = _marcas.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadMarcas()
    }

    private fun loadMarcas() {
        viewModelScope.launch {
            try {
                _marcas.value = repository.getMarcas()
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error al cargar las marcas: ${e.message}"
            }
        }
    }

    fun createMarca(nombre: String, imagenUrl: String) {
        viewModelScope.launch {
            try {
                repository.createMarca(nombre, imagenUrl)
                loadMarcas() // Refresca la lista
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error al crear la marca: ${e.message}"
            }
        }
    }

    fun updateMarca(id: Int, nombre: String, imagenUrl: String) {
        viewModelScope.launch {
            try {
                repository.updateMarca(id, nombre, imagenUrl)
                loadMarcas() // Refresca la lista
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error al actualizar la marca: ${e.message}"
            }
        }
    }

    fun deleteMarca(id: Int) {
        viewModelScope.launch {
            try {
                repository.deleteMarca(id)
                loadMarcas() // Refresca la lista
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error al eliminar la marca: ${e.message}"
            }
        }
    }
}
