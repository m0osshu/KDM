package com.example.myautoo.ui.feature.detail

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myautoo.R
import com.example.myautoo.ui.components.SpecCard
import androidx.compose.foundation.layout.Arrangement

@Composable
fun DetailSpecs(totalCapacity: String, highestSpeed: String, engineOutput: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ){
        SpecCard(R.drawable.sit, "Capacidad\ntotal", totalCapacity, Modifier.weight(1f))
        SpecCard(R.drawable.speed, "velocidad\nmaxima", highestSpeed, Modifier.weight(1f))
        SpecCard(R.drawable.engine, "tipo de\nmotor", engineOutput, Modifier.weight(1f))
    }
}