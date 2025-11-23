package com.example.myautoo.ui.feature.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myautoo.R
import java.text.NumberFormat
import java.util.Locale

@Composable
fun DetailPriceBar(price: Double, onAddToCart: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                "precio",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                "$" + NumberFormat
                    .getNumberInstance(Locale.US)
                    .format(price),
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp
            )
        }
        Button(
            onClick = onAddToCart,
            modifier = Modifier
                .height(60.dp)
                .width(120.dp), // Botón más alargado
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black, // Fondo negro
                contentColor = Color.White // Icono blanco
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.btn_3),
                contentDescription = "Añadir al carrito",
                modifier = Modifier.size(30.dp)
            )
        }
    }
}