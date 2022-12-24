package com.oe.moviesearcher2.ui

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.oe.moviesearcher2.ui.theme.*
import com.oe.moviesearcher2.R
import com.oe.moviesearcher2.ui.mainscreens.FavoritesMainScreen
import com.oe.moviesearcher2.ui.mainscreens.MoviesMainScreen
import com.oe.moviesearcher2.ui.mainscreens.MusicsMainScreen
import com.oe.moviesearcher2.ui.mainscreens.SeriesMainScreen
import com.oe.moviesearcher2.ui.viewmodels.MainViewModel
import kotlinx.coroutines.launch

data class BottomMenuContent(
    val title: String,
    @DrawableRes val iconId: Int
)

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalPagerApi::class)
@ExperimentalFoundationApi
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    onNavigateToMovies: (Int) -> Unit
) {

    val scaffoldState = rememberScaffoldState()

    val pageState = rememberPagerState(0)
    val scope = rememberCoroutineScope()

    Scaffold(
        Modifier.background(green600),
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomMenuTabView(
                items = listOf(
                    BottomMenuContent("Movies", R.drawable.ic_movie),
                    BottomMenuContent("Series", R.drawable.ic_tv),
                    BottomMenuContent("Musics", R.drawable.ic_music),
                    BottomMenuContent("Favorites", R.drawable.ic_favorite),
                    BottomMenuContent("Profile", R.drawable.ic_profile),
                ),
                pageState = pageState
            )
            {
                scope.launch {
                    pageState.animateScrollToPage(it)
                }
            }
        }
    ) {
        Column(modifier = Modifier.padding(bottom = it.calculateBottomPadding())) {
            ChipSection(
                chips = listOf("Movies", "Series", "Musics", "Favorites", "Uploads"),
                onNavigateToMovies
            )
            HorizontalPager(
                state = pageState,
                count = 5,
            ) { page ->
                when(page) {
                    0 -> MoviesMainScreen()
                    1 -> SeriesMainScreen()
                    2 -> MusicsMainScreen()
                    3 -> FavoritesMainScreen()
                    4 -> ColorSection(greenA400)
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun ColorSection(color: Color) {
    Column(modifier= Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(color)){
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BottomMenuTabView(
    modifier: Modifier = Modifier,
    items: List<BottomMenuContent>,
    activeHighlightColor: Color = greenA700,
    activeTextColor: Color = Color.White,
    inactiveTextColor: Color = Color.White,
    pageState: PagerState,
    onTabSelected: (selectedIndex: Int) -> Unit
) {
    TabRow(
        selectedTabIndex = pageState.currentPage,
        backgroundColor = green800,
        divider = {TabRowDefaults.Divider(color = Color.Transparent)},
        indicator = {TabRowDefaults.Indicator(color = Color.Transparent)},
        modifier = modifier
    ) {
        items.forEachIndexed { index, item ->
            BottomMenuTab(
                item = item,
                isSelected = index == pageState.currentPage,
                activeHighlightColor = activeHighlightColor,
                activeTextColor = activeTextColor,
                inactiveTextColor = inactiveTextColor,
                onItemClick = {
                    onTabSelected(index)
                }
            )
        }
    }
}

@Composable
fun BottomMenuTab(
    item: BottomMenuContent,
    isSelected: Boolean = false,
    activeHighlightColor: Color = green300,
    activeTextColor: Color = Color.White,
    inactiveTextColor: Color = Color.White,
    onItemClick: () -> Unit
) {
    Tab(selected = isSelected, onClick = onItemClick) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(top=10.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (isSelected) activeHighlightColor else Color.Transparent)
                    .padding(10.dp)
            ) {
                Icon(
                    painter = painterResource(id = item.iconId),
                    contentDescription = item.title,
                    tint = if(isSelected) activeTextColor else inactiveTextColor,
                    modifier = Modifier.size(20.dp)
                )
            }
            Text(
                text = item.title,
                color = if(isSelected) activeTextColor else inactiveTextColor
            )
        }
    }
}


@Composable
fun ChipSection(
    chips: List<String>,
    onNavigateToMovies: (Int) -> Unit
) {
    var selectedChipIndex:Int by rememberSaveable {
        mutableStateOf(0)
    }
    LazyRow {
        items(chips.size) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(start = 15.dp, top = 15.dp, bottom = 15.dp)
                    .clickable {
                        onNavigateToMovies(it)
                        selectedChipIndex = it
                    }
                    .clip(RoundedCornerShape(10.dp))
                    .background(green900)
                    .padding(15.dp)
            ) {
                Text(text = chips[it], color = TextWhite)
            }
        }
        item{
            Spacer(
                modifier = Modifier
                    .width(15.dp)
            )
        }
    }
}


