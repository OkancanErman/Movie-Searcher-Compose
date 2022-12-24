package com.oe.moviesearcher2.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.oe.moviesearcher2.ui.theme.*
import com.oe.moviesearcher2.ui.viewmodels.SeriesViewModel


@SuppressLint("SuspiciousIndentation")
@ExperimentalFoundationApi
@Composable
fun SeriesInfoScreen(
    seriesId: String,
    viewModel: SeriesViewModel = hiltViewModel(),
    onNavigateToInfo: () -> Unit
) {

    Column(modifier = Modifier) {

    }
}

