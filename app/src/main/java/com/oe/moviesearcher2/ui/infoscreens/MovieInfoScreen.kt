package com.oe.moviesearcher2.ui.infoscreens

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.oe.moviesearcher2.ui.theme.*
import com.oe.moviesearcher2.ui.viewmodels.MoviesViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter

@SuppressLint("SuspiciousIndentation")
@ExperimentalFoundationApi
@Composable
fun MovieInfoScreen(
    movieId: Int,
    viewModel: MoviesViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    onNavigateToInfo: () -> Unit
) {
    val details = viewModel.details.collectAsState().value[movieId]
    val listOfMovieSubHeader = arrayListOf<MovieSubHeader>(
        MovieSubHeader("Genre",details.genre!!),
        MovieSubHeader("Imdb Rating",details.imdbRating!!),
        MovieSubHeader("Language",details.language!!),
        MovieSubHeader("Directed by",details.director!!),
        MovieSubHeader("Running Time",details.runtime!!),
        MovieSubHeader("Actors",details.actors!!),
        MovieSubHeader("Awards",details.awards!!),
    )

    var desiredItemMinHeight by remember {
        mutableStateOf(0.dp)
    }

    val density = LocalDensity.current

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (image, info, topBar, title, content) = createRefs()
        Image(
            painter = rememberAsyncImagePainter(details.poster),
            contentDescription = details.title,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .constrainAs(image) {
                    linkTo(
                        start = parent.start,
                        top = parent.top,
                        end = parent.end,
                        bottom = info.top,
                        bottomMargin = (-32).dp
                    )
                    width = Dimension.fillToConstraints
                }
        )

        DetailsAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .constrainAs(topBar) {
                    linkTo(start = parent.start, end = parent.end)
                    top.linkTo(parent.top)
                    width = Dimension.fillToConstraints
                }
        ) { onBackPressed() }

        Text(
            text = details.title ?: "",
            style = MaterialTheme.typography.h6,
            color = Color.White,
            modifier = Modifier
                .background(color = Color.Black.copy(alpha = 0.6f))
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 8.dp,
                    bottom = 8.dp
                )
                .constrainAs(title) {
                    linkTo(start = parent.start, end = parent.end)
                    bottom.linkTo(info.top)
                },
            textAlign = TextAlign.Center,
        )

        Spacer(
            modifier = Modifier
                .height(40.dp)
                .background(color = Color.Black.copy(alpha = 0.6f))
                .constrainAs(info) {
                    linkTo(start = parent.start, end = parent.end)
                    bottom.linkTo(image.bottom)
                    width = Dimension.fillToConstraints
                }
        )

        Surface(
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
            modifier = Modifier
                .shadow(5.dp,shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .constrainAs(content) {
                    linkTo(start = parent.start, end = parent.end)
                    top.linkTo(info.top)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            color = MaterialTheme.colors.surface,
            elevation = 5.dp,
        ) {

            Column(
                modifier = Modifier
                    .background(green900)
            ){

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp)
                ) {

                    item(span= { GridItemSpan(2) }){

                        Text(
                            text = details.plot!!,
                            style = MaterialTheme.typography.body2,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(
                                    vertical = 20.dp
                                ),
                        )
                    }
                    items(listOfMovieSubHeader.size) {
                        MovieSubHeaderXd(listOfMovieSubHeader[it], Modifier
                            .onPlaced {
                                with(density) {
                                    if (desiredItemMinHeight < it.size.height.toDp()) {
                                        desiredItemMinHeight = it.size.height.toDp()
                                    }
                                }
                            }
                            .defaultMinSize(minHeight = desiredItemMinHeight),
                        )
                    }
//                    item(span= { GridItemSpan(2) }){
//
//                        Text(
//                            text = details.plot!!,
//                            style = MaterialTheme.typography.body1,
//                            color = Color.White,
//                        )
//                    }
                }

            }

        }


    }
}

@Composable
fun DetailsAppBar(modifier: Modifier, onBackPressed: () -> Unit) {
    ConstraintLayout(modifier) {
        val (back, share) = createRefs()
        IconButton(
            onClick = onBackPressed,
            Modifier
                .constrainAs(back) {
                    start.linkTo(parent.start, margin = 8.dp)
                    top.linkTo(parent.top, margin = 8.dp)
                }
                .background(Color.Black.copy(alpha = 0.4f), CircleShape)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

    }
}

@Composable
fun MovieSubHeaderXd(
    movieSubHeaderItem: MovieSubHeader,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .padding(10.dp)
            .background(green600, RoundedCornerShape(10.dp))
            .clickable {

            },
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 5.dp),
            text = movieSubHeaderItem.title,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 5.dp),
            text = movieSubHeaderItem.content,
            color = Color.White
        )
    }
}

data class MovieSubHeader(
    val title: String,
    val content: String,
)

