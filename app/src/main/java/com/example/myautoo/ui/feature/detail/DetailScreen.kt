package com.example.myautoo.ui.feature.detail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.myautoo.data.model.CarModel
import com.example.myautoo.ui.viewModel.CartViewModel
import com.example.myautoo.ui.viewModel.CategoryViewModel

@Composable
fun DetailScreen(
    car: CarModel,
    categoryViewModel: CategoryViewModel,
    cartViewModel: CartViewModel,
    onBack: () -> Unit
) {
    val scroll = rememberScrollState()
    val context = LocalContext.current
    val categories = categoryViewModel.categories.value
    val matchedMarca = categories.find { it.nombre.equals(car.brand, ignoreCase = true) }

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        DetailHeader(car.picUrl, onBack, onBack)
        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 450.dp)
                .verticalScroll(state = scroll)
        ) {
            DetailInfo(car.title,  car.description)
            DetailSpecs(
                brand = matchedMarca?.nombre ?: car.brand,
                brandImageUrl = matchedMarca?.imagenMarca ?: car.picUrl,
                rating = car.rating
            )
            DetailPriceBar(
                price = car.price,
                onAddToCart = {
                    cartViewModel.addToCart(car)
                    Toast.makeText(context, "${car.title} añadido al carrito", Toast.LENGTH_SHORT).show()
                }
            )
            Spacer(Modifier.height(24.dp))
        }
    }
}