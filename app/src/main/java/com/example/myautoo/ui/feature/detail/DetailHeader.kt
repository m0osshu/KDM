package com.example.myautoo.ui.feature.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myautoo.R
import com.example.myautoo.ui.components.TopBar

@Composable
fun DetailHeader(
    picUrl: String?,
    onBack: () -> Unit,
    onFav: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(500.dp)
            .background(color = Color.Black)
    ) {
        TopBar(
            title = "car Detail",
            backIconRes = R.drawable.back1,
            onBack = onBack,
            onTrailingClick = onFav,
            titleColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
        )

        AsyncImage(
            model = picUrl,
            contentDescription = null,
            contentScale = ContentScale.Inside,
            placeholder = painterResource(R.drawable.intro_car),
            error = painterResource(R.drawable.intro_car),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp, top = 32.dp)
                .fillMaxSize()
                .height(220.dp)
        )
    }
}