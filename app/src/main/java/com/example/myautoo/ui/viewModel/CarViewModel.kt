package com.example.myautoo.ui.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myautoo.data.model.CarModel
import androidx.compose.runtime.State
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class CarViewModel : ViewModel() {
    private val _cars = MutableStateFlow<List<CarModel>>(emptyList())
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    val cars = combine(_cars, _searchText) { cars, text ->
        if (text.isBlank()) {
            cars
        } else {
            cars.filter { it.title.contains(text, ignoreCase = true) }
        }
    }

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    init {
        viewModelScope.launch {
            fetchCars()
        }
    }

    fun onSearchTextChanged(text: String) {
        _searchText.value = text
    }

    private fun fetchCars() {
        try {
            val ref = FirebaseDatabase.getInstance().getReference("Cars")

            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val temp = mutableListOf<CarModel>()
                    for (child in snapshot.children) {
                        try {
                            child.getValue(CarModel::class.java)?.let {
                                temp.add(it)
                            }
                        } catch (e: Exception) {
                            _error.value = "Error parsing car data: ${e.message}"
                        }
                    }
                    _cars.value = temp
                    _isLoading.value = false
                }

                override fun onCancelled(error: DatabaseError) {
                    _isLoading.value = false
                    _error.value = "Firebase error: ${error.message}"
                }
            })
        } catch (e: Exception) {
            _isLoading.value = false
            _error.value = "Initialization error: ${e.message}"
        }
    }
}