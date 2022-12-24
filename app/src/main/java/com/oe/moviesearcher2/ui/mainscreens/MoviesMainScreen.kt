package com.oe.moviesearcher2.ui.mainscreens

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oe.moviesearcher2.R
import com.oe.moviesearcher2.ui.theme.*
import com.oe.moviesearcher2.util.MarqueeText


@SuppressLint("SuspiciousIndentation")
@ExperimentalFoundationApi
@Composable
fun MoviesMainScreen() {
    Column(modifier = Modifier.background(green600)) {
        val movieList = List(10) { MovieDemo("Movie", teal500) }
        MovieSectionDemo(
            movieList = movieList,
        )
    }
}

@ExperimentalFoundationApi
@Composable
fun MovieSectionDemo(movieList: List<MovieDemo>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp),
            modifier = Modifier.fillMaxHeight()
        ) {
            items(movieList.size) {
                MovieItemDemo(movieItem = movieList[it])
            }
        }
    }
}

@Composable
fun MovieItemDemo(
    movieItem: MovieDemo,
) {
    BoxWithConstraints(
        modifier = Modifier
            .padding(7.5.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(movieItem.backgroundColor)
            .clickable {
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_movie),
                contentDescription = movieItem.movieName,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )

            Box(modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        ),
                        startY = 300f
                    )
                )
            )

            MarqueeText(
                gradientEdgeColor = Color.Black,
                text = movieItem.movieName,
                style = MaterialTheme.typography.h2,
                lineHeight = 26.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            )

        }
    }
}

data class MovieDemo(
    val movieName: String,
    val backgroundColor: Color,
)
