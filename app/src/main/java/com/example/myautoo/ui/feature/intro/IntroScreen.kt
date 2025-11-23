package com.example.myautoo.ui.feature.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myautoo.R

@Composable
fun IntroScreen(navToMain: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 48.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Fácil de comprar\n tu auto soñado!",
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 24.dp)
                        .statusBarsPadding(),
                    fontSize = 48.sp,
                    style = TextStyle(lineBreak = LineBreak.Paragraph),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(height = 24.dp))
                Text(
                    text = "Desliza, compara y elige. Tu nueva forma de moverte te espera. Desde compactos ideales para la ciudad hasta SUVs con actitud para el plan de fin de semana. Tenemos marcas y modelos que buscan lo mismo que tú, rendimiento, tecnología, diseño y la mejor conexión con tu día a día.",
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 24.dp),
                    fontSize = 14.sp,
                    lineHeight = 24.sp
                )
            }
            Column {
                Image(
                    painter = painterResource(id = R.drawable.intro_car),
                    contentDescription = "Auto deportivo moderno",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 200.dp)

                )

                Spacer(modifier = Modifier.height(height = 32.dp))

                Button(
                    onClick = navToMain,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .height(height = 60.dp),
                    shape = RoundedCornerShape(size = 50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = "Empezar",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IntroScreenPreview() {
    IntroScreen()
}