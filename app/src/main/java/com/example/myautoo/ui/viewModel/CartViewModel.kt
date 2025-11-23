package com.example.myautoo.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myautoo.data.model.CarModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CarModel>>(emptyList())
    val cartItems: StateFlow<List<CarModel>> = _cartItems.asStateFlow()

    val totalPrice: StateFlow<Double> = cartItems
        .map { items -> items.sumOf { it.price } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0.0
        )

    fun addToCart(car: CarModel) {
        // Evitar duplicados
        if (!_cartItems.value.contains(car)) {
            _cartItems.value = _cartItems.value + car
        }
    }

    fun removeFromCart(car: CarModel) {
        _cartItems.value = _cartItems.value.filterNot { it.title == car.title }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }
}
