package com.example.myautoo.ui.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.myautoo.R
import com.example.myautoo.ui.viewModel.CarViewModel

@Composable
fun SearchSection(carViewModel: CarViewModel) {
    val searchText by carViewModel.searchText.collectAsState()

    ConstraintLayout(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(50.dp)
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(50.dp))
            .padding(horizontal = 4.dp)
    ) {
        val (searchInput, iconBox) = createRefs()
        TextField(
            value = searchText,
            onValueChange = carViewModel::onSearchTextChanged,
            placeholder = { Text(text = "buscar auto...") },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.search_icon),
                    contentDescription = null
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .constrainAs(searchInput) {
                    start.linkTo(parent.start)
                    end.linkTo(iconBox.start, margin = 8.dp)
                    width = Dimension.fillToConstraints
                }
        )
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color.White, CircleShape)
                .constrainAs(iconBox) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                contentDescription = null,
                painter = painterResource(R.drawable.settings),
                tint = Color.White
            )
        }
    }
}