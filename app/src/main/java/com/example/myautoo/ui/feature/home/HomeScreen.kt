package com.example.myautoo.ui.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myautoo.data.model.CarModel
import com.example.myautoo.ui.viewModel.CarViewModel
import com.example.myautoo.ui.viewModel.CategoryViewModel

@Composable
fun MainScreen(
    onProfileClick: () -> Unit,
    onCarClick: (CarModel) -> Unit,
    onCartClick: () -> Unit,
    carViewModel: CarViewModel,
    categoryViewModel: CategoryViewModel
) {
    val categories by categoryViewModel.categories
    val isLoadingCategory by categoryViewModel.isLoading
    val cars by carViewModel.cars.collectAsState(initial = emptyList())
    val isLoadingCars by carViewModel.isLoading
    val error by carViewModel.error

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xffb0b0b0))
        ) {
            item {
                HeaderSection(carViewModel = carViewModel)
            }
            item {
                if (error != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Error: $error",
                            color = Color.Red
                        )
                    }
                } else if (isLoadingCars) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp), // ALTURA FIJA
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    CategoryList(
                        categories = categories,
                        modifier = Modifier
                    )
                }
            }
            item {
                Spacer(Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color.White,
                            RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("mas vendidos", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (isLoadingCars) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp), // ALTURA FIJA
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    } else if (cars.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No hay autos disponibles",
                                color = Color.Gray
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(800.dp) // ALTURA FIJA PARA EL GRID
                        ) {
                            PopularList(cars, onCarClick = onCarClick)
                        }
                    }
                }
            }
        }

        // BOTTOM NAV BAR - AGREGADO
        BottomNavBar(
            onProfileClick = onProfileClick,
            onCartClick = onCartClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 24.dp)
        )
    }
}