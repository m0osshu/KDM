package com.example.myautoo.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myautoo.R

@Composable
fun SpecCard(iconRes: Int, title: String, value: String, modifier: Modifier= Modifier) {
    Column(modifier=modifier.clip(RoundedCornerShape(10.dp))
        .background(colorResource(R.color.grey))
        .padding(top = 16.dp, bottom = 12.dp)
    ) {
        Image(
            painterResource(iconRes),
            contentDescription = null,
            modifier=Modifier.padding(start = 16.dp)
        )
        Text(
            text = title,
            color = colorResource(R.color.black),
            fontSize = 16.sp,
            modifier=Modifier.padding(start = 16.dp)

        )
        Text(
            text = value,
            color = colorResource(R.color.black),
            fontWeight = FontWeight.Bold,
            modifier=Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp)

        )
    }
}