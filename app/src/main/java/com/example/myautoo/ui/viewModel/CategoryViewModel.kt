package com.example.myautoo.ui.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myautoo.data.model.CategoryModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class CategoryViewModel: ViewModel() {
    private val _categories = mutableStateOf<List<CategoryModel>>(emptyList())
    val categories: State<List<CategoryModel>> = _categories

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    init {
        viewModelScope.launch {
            loadCategories()
        }
    }

    private fun loadCategories() {
        try {
            val database = FirebaseDatabase.getInstance()
            val ref = database.getReference("Category")

            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = ArrayList<CategoryModel>()
                    snapshot.children.forEach { dataSnapshot ->
                        try {
                            dataSnapshot.getValue(CategoryModel::class.java)?.let { item ->
                                list.add(item)
                            }
                        } catch (e: Exception) {
                            _error.value = "Error parsing category: ${e.message}"
                        }
                    }
                    _categories.value = list
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