package fhnw.emoba.freezerapp.ui

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import fhnw.emoba.freezerapp.model.FreezerModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import fhnw.emoba.R
import fhnw.emoba.freezerapp.data.dataClasses.Song
import fhnw.emoba.freezerapp.ui.screens.MainScreen
import fhnw.emoba.freezerapp.ui.screens.Screen

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Radio,
        BottomBarScreen.Albums,
        BottomBarScreen.Songs,
        BottomBarScreen.Favorites
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation() {
        screens.forEach { screens ->
            AddItem(
                screen = screens,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        label = {
            Text(text = screen.title)
        }, icon = {
            Icon(imageVector = screen.icon, contentDescription = "Navigation Icon")
        })
}

@Composable
fun Bar(model: FreezerModel) {
    with(model) {
        TopAppBar(
            title = {
                Row(
                    modifier = Modifier.padding(start = 0.dp, end = 15.dp)
                ) {
                    Text(
                        text = appTitle,
                        fontSize = 25.sp,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.weight(1.0f))
                    ImageWithBlockingUI(
                        resId = R.drawable.deezerlogo,
                        description = "Deezer Logo",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(120.dp)
                    )
                }
            },
        )
    }
}

@Composable
fun BackBar(model: FreezerModel) {
    with(model) {
        TopAppBar(
            title = {
                Text(
                    text = appTitle,
                    fontSize = 25.sp,
                )
            },
            actions = {
                IconButton(onClick = {
                    currentScreen = when (currentScreen) {
                        Screen.RADIOTRACKSSCREEN -> {
                            Screen.MAINSCREEN
                        }
                        else -> {
                            Screen.MAINSCREEN // should refer to AlbumsScreen
                        }
                    }
                }) {
                    Icon(Icons.Filled.Close, "Close icon")
                }
            }
        )
    }
}

@Composable
fun ScreenTitle(title: String) {
    Row(
        modifier = Modifier.padding(all = 15.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h4,
        )
    }
}

@Composable
fun LoadingScreen(loadingMessage: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(loadingMessage, style = MaterialTheme.typography.h5)
        CircularProgressIndicator(modifier = Modifier.padding(10.dp))
    }
}

@Composable
fun NoResultsMessage(message: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(message, style = MaterialTheme.typography.h5)
    }
}

@Composable
fun ImageWithBlockingUI(
    @DrawableRes resId: Int,
    description: String,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = resId),
        contentDescription = description,
        contentScale = ContentScale.FillWidth,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TrackItem(track: Song, model: FreezerModel) {
    with(track) {
        ListItem(
            text = {
                Text(
                    title,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            },
            secondaryText = {
                Text(
                    artistName,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            },
            icon = {
                Image(
                    cover, "Album cover", modifier = Modifier
                        .size(width = 80.dp, height = 80.dp)
                        .shadow(15.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            },
            trailing = {
                TrailingListIcons(track = track, model = model)
            })
    }
    Divider()
}

// inspired by Alison Fersch
@Composable
fun TrailingListIcons(track: Song, model: FreezerModel) {
    with(track) {
        Column(modifier = Modifier.fillMaxHeight()) {
            IconToggleButton(checked = isFavorite, onCheckedChange = {
                isFavorite = if (isFavorite) {
                    model.removeFavoriteSong(track)
                    false
                } else {
                    model.addFavoriteSong(track)
                    true
                }
            }) {
                val tint by animateColorAsState(
                    if (isFavorite) {
                        MaterialTheme.colors.primaryVariant
                    } else {
                        MaterialTheme.colors.onSurface
                    }
                )
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "favorite icon",
                    tint = tint,
                    modifier = Modifier.size(25.dp)
                )
            }
            PlayPauseButton(track = track, model = model)
        }
    }
}

// inspired by Alison Fersch
@Composable
fun PlayPauseButton(track: Song, model: FreezerModel) {
    with(model) {
        if (currentSong == track && !playerIsReady) {
            PauseButton(track = track, model = model)
        } else {
            PlayButton(track = track, model = model)
        }
    }
}

@Composable
fun PauseButton(track: Song, model: FreezerModel) {
    with(model) {
        IconButton(onClick = {
            pausePlayer()
            currentSong = null
        }) {
            Icon(
                Icons.Filled.Pause, "pauseButton Icon",
                tint = MaterialTheme.colors.primary,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
fun PlayButton(track: Song, model: FreezerModel) {
    with(model) {
        IconButton(
            onClick = {
                currentSong = track
                when (currentScreen) {
                    Screen.RADIOTRACKSSCREEN -> {
                        currentPlayList = radioStationTracksList
                    }
                    Screen.ALBUMTRACKSSCREEN -> {
                        currentPlayList = albumsTracksList
                    }
                }
                startPlayer()
            }) {
            Icon(
                Icons.Filled.PlayArrow, "playButton Icon",
                tint = MaterialTheme.colors.onSurface,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
private fun PlayFromStartButton(model: FreezerModel) {
    with(model) {
        IconButton(onClick = { fromStart() }) {
            Icon(
                Icons.Filled.RestartAlt, "restartButton Icon",
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
private fun GoBackButton(model: FreezerModel) {
    with(model) {
        IconButton(onClick = { playPreviousSong() }) {
            Icon(
                Icons.Filled.SkipPrevious, "skipPrevious Icon",
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
private fun SkipButton(model: FreezerModel) {
    with(model) {
        IconButton(onClick = { playNextSong() }) {
            Icon(
                Icons.Filled.SkipNext, "skipNext Icon",
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SongPlayer(track: Song, model: FreezerModel) {
    Card(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp)
            .shadow(15.dp)
            .background(Color.Transparent),
        shape = RoundedCornerShape(15.dp)
    ) {
        with(track) {
            ListItem(
                text = {
                    Text(
                        title,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                },
                secondaryText = {
                    Text(
                        artistName,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                },
                icon = {
                    Image(
                        cover, "Album cover", modifier = Modifier
                            .size(width = 50.dp, height = 50.dp)
                            .shadow(15.dp)
                            .clip(RoundedCornerShape(5.dp))
                    )
                }, trailing = {
                    Row() {
                        PlayFromStartButton(model = model)
                        GoBackButton(model = model)
                        PlayPauseButton(track = track, model = model)
                        SkipButton(model = model)
                    }
                }
            )
        }
    }
}

@Composable
fun CardHeadline(text: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onBackground
        )
    }
}