package com.oe.moviesearcher2.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.oe.moviesearcher2.ui.theme.*
import com.oe.moviesearcher2.ui.viewmodels.SeriesViewModel


@SuppressLint("SuspiciousIndentation")
@ExperimentalFoundationApi
@Composable
fun SeriesScreen(
    viewModel: SeriesViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    onNavigateToInfo: (String) -> Unit
) {
    Column(modifier = Modifier.background(green600).fillMaxSize()) {
        IconButton(
            onClick = {
                onBackPressed()
            },
            Modifier
                .padding(top = 15.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
            )
        }
    }
}