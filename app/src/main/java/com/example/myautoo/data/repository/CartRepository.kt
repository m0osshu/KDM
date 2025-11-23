package com.example.myautoo.data.repository

import com.example.myautoo.data.local.dao.CartDao
import com.example.myautoo.data.local.entity.CartItemEntity

class CartRepository(private val cartDao: CartDao) {

    val cartItems = cartDao.getCartItems()

    suspend fun addCar(item: CartItemEntity) {
        cartDao.insertItem(item)
    }

    suspend fun removeCar(item: CartItemEntity) {
        cartDao.deleteItem(item)
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }
}