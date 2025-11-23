package com.example.myautoo.ui.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myautoo.R

@Composable
@Preview
fun BottomNavBar(
    onHomeClick: () -> Unit = {},
    onCartClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = Color.Transparent,
        shape = RoundedCornerShape(50.dp),
        shadowElevation = 8.dp
    ) {
        BottomAppBar(
            containerColor = Color.Black,
            contentColor = Color.White,
            contentPadding = PaddingValues(horizontal = 16.dp),
            tonalElevation = 0.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                horizontalArrangement = Arrangement.SpaceAround, // Usando SpaceAround para alinear
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onHomeClick) {
                    Icon(
                        painter = painterResource(R.drawable.btn_1),
                        contentDescription = "Inicio",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                IconButton(onClick = onCartClick) {
                    Icon(
                        painter = painterResource(R.drawable.btn_3),
                        contentDescription = "Carrito",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                IconButton(onClick = onProfileClick) {
                    Icon(
                        painter = painterResource(R.drawable.btn_4),
                        contentDescription = "Perfil",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}