package com.example.myautoo.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myautoo.data.local.entity.CartItemEntity
import com.example.myautoo.data.repository.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.example.myautoo.data.model.CarModel


class CartViewModel (
    private val repository: CartRepository
) : ViewModel() {

    val cartItems = repository.cartItems
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


    val totalPrice: StateFlow<Double> = cartItems
        .map { items -> items.sumOf { it.price } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = 0.0
        )

    fun addToCart(car: CarModel) {
        viewModelScope.launch {
            repository.addCar(
                CartItemEntity(
                    carId = car.id,
                    title = car.title, // typo: debería ser `title` en el modelo CarModel
                    price = car.price // typo: debería ser `price` en el modelo CarModel
                )
            )
        }
    }

    fun removeFromCart(item: CartItemEntity) {
        viewModelScope.launch {
            repository.removeCar(item)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
        }
    }
}
