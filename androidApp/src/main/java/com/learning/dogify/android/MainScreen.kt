package com.learning.dogify.android


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.learning.dogify.model.Breed
import kotlinx.coroutines.launch


@Composable
fun MainScreen(viewModel: MainViewModel) {
    val state by viewModel.state.collectAsState()
    val breeds by viewModel.breeds.collectAsState()
    val events by viewModel.event.collectAsState(Unit)
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val shouldFilterFavorites by viewModel.shouldFilterFavorites.collectAsState()

    val scaffoldState = rememberScaffoldState()
    val snackBarCoroutineScope = rememberCoroutineScope()

    Scaffold(scaffoldState = scaffoldState) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
            onRefresh = viewModel::refresh
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Row(
                    Modifier
                        .wrapContentWidth(Alignment.End)
                        .padding(8.dp)
                ) {
                    Text(text = "Filter Favorites")
                    Switch(checked = shouldFilterFavorites, onCheckedChange = {
                        viewModel.onToggleFavouriteFilter()
                    })
                }
                //----------------data-------------------//

                when (state) {
                    State.LOADING -> {
                        Spacer(modifier = Modifier.weight(1f))
                        CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    State.NORMAL -> {
                        Breeds(breeds, viewModel::onFavouriteTapped)
                    }
                    State.ERROR -> {
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = "Oops something went wrong...",
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(Modifier.weight(1f))
                    }
                    State.EMPTY -> {
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = "Oops look lik there are no ${
                                if (shouldFilterFavorites)
                                    "favorites"
                                else
                                    "dogs"
                            }"
                        )
                        Spacer(modifier = Modifier.weight(1f))

                    }
                }

            }

        }

        if (events == Event.Error) {
            snackBarCoroutineScope.launch {
                scaffoldState.snackbarHostState.apply {
                    currentSnackbarData?.dismiss()
                    showSnackbar("Oops something went wrong")
                }
            }
        }

    }


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Breeds(
    breeds: List<Breed>, onFavouriteTapped:
        (Breed) -> Unit = {}
) {
    LazyVerticalGrid(cells = GridCells.Fixed(2)) {
        items(breeds) {
            Column(Modifier.padding(8.dp)) {
                Image(
                    painter = rememberCoilPainter(request = it.imageUrl),
                    contentDescription = "${it.name}-image",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    contentScale = ContentScale.Crop
                )
                Row(Modifier.padding(8.dp)) {
                    Text(
                        text = it.name,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        if (it.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Mark as favorite",
                        modifier = Modifier.clickable { onFavouriteTapped(it) }
                    )
                }
            }
        }

    }
}

@Preview
@Composable
fun BreedsPreview() {
    MaterialTheme {
        Surface {
            Breeds(breeds = (0 until 10).map {
                Breed(
                    name = "Breed $it",
                    imageUrl = "",
                    isFavorite = it % 2 == 0
                )
            })
        }
    }
}