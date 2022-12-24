package com.oe.moviesearcher2.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.oe.moviesearcher2.ui.theme.*
import com.oe.moviesearcher2.ui.viewmodels.MoviesViewModel
import com.oe.moviesearcher2.util.MarqueeText


@SuppressLint("SuspiciousIndentation")
@ExperimentalFoundationApi
@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    onNavigateToMovie: (Int) -> Unit
) {

    val searchList by viewModel.movies.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val searchString by viewModel.searchString.collectAsState("Batman")

    Column(modifier = Modifier.background(green600)) {

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, bottom = 15.dp, end = 15.dp)
        ) {

            IconButton(
                onClick = {
                    onBackPressed()
                          },
                Modifier
                    .offset(y = 5.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp)
                )
            }
            SearchBarMovie(searchString = searchString,
                onSearchStringChange = { viewModel.onSearchStringChange(it) },
                onIconClick = {
                    viewModel.onSearchButtonClick()
                }
            )
        }

        if(uiState == MoviesViewModel.Status.Loading(null))
            LoadingMovies()
        else if(uiState == MoviesViewModel.Status.Success(null) && searchList.isEmpty())
            EmptyResult()
        else if(uiState is MoviesViewModel.Status.Error)
            EmptyResult()
        else {
            val movieList: List<Movie> =
                searchList.map { Movie(it.title!!, it.poster!!, Color.DarkGray, it.year!!) }
            MovieSection(
                movieList = movieList,
                onNavigateToMovie
            )
        }
    }
}

@Composable
fun SearchBarMovie(
    searchString: String = "Batman",
    onIconClick: () -> Unit,
    onSearchStringChange: (String) -> Unit
) {

    OutlinedTextField(
        trailingIcon = @Composable {
            IconButton(
                onClick = onIconClick,
            ) {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        },
        value = searchString,
        onValueChange = onSearchStringChange,
        textStyle = MaterialTheme.typography.h2,
        label = { Text("Search", color = Color.White) },
        placeholder = { Text("Search", color = Color.LightGray) },
        maxLines = 1
    )

}

@Composable
fun LoadingMovies() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        CircularProgressIndicator(
            color = MaterialTheme.colors.primary,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun EmptyResult() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text("No Results", Modifier.align(Alignment.Center))
    }
}

@ExperimentalFoundationApi
@Composable
fun MovieSection(movieList: List<Movie>, onNavigateToMovie: (Int) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp),
            modifier = Modifier.fillMaxHeight()
        ) {
            items(movieList.size) {
                MovieItem(movieItem = movieList[it], index = it, onNavigateToMovie)
            }
        }
    }
}

@Composable
fun MovieItem(
    movieItem: Movie,
    index: Int,
    onNavigateToMovie: (Int) -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .padding(7.5.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(movieItem.backgroundColor)
            .clickable {
                onNavigateToMovie(index)
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(movieItem.imgUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = movieItem.movieName,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
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

data class Movie(
    val movieName: String,
    val imgUrl: String,
    val backgroundColor: Color,
    val age: String
)