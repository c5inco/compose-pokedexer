package des.c5inco.pokedexer.ui.pokedex

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.ExperimentalTransitionApi
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import des.c5inco.pokedexer.R
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.data.pokemon.mapSampleEvolutionsToList
import des.c5inco.pokedexer.data.pokemon.mapSampleMovesToDetailsList
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.ui.common.Emphasis
import des.c5inco.pokedexer.ui.common.Material3Transitions
import des.c5inco.pokedexer.ui.common.NavigationTopAppBar
import des.c5inco.pokedexer.ui.common.PokeBall
import des.c5inco.pokedexer.ui.common.PokemonTypeLabels
import des.c5inco.pokedexer.ui.common.TypeLabelMetrics.Companion.MEDIUM
import des.c5inco.pokedexer.ui.common.formatId
import des.c5inco.pokedexer.ui.common.nestedScrollConnection
import des.c5inco.pokedexer.ui.pokedex.section.AboutSection
import des.c5inco.pokedexer.ui.pokedex.section.BaseStatsSection
import des.c5inco.pokedexer.ui.pokedex.section.EvolutionSection
import des.c5inco.pokedexer.ui.pokedex.section.MovesSection
import des.c5inco.pokedexer.ui.theme.AppTheme
import des.c5inco.pokedexer.ui.theme.PokemonTypesTheme
import kotlin.math.roundToInt

@Composable
fun AnimatedContentScope.PokemonDetailsScreenRoute(
    viewModel: PokedexViewModel,
    detailsViewModel: PokemonDetailsViewModel,
    onBackClick: () -> Unit,
) {
    PokemonDetailsScreen(
        loading = viewModel.uiState.loading,
        pokemonSet = viewModel.uiState.pokemon,
        pokemon = detailsViewModel.details,
        evolutions = detailsViewModel.evolutions,
        moves = detailsViewModel.moves,
        isFavorite = detailsViewModel.isFavorite,
        onPage = {
            detailsViewModel.refresh(it)
        },
        onFavoriteClick = {
            detailsViewModel.toggleFavorite(it)
        },
        onBackClick = onBackClick,
    )
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
@Composable
fun AnimatedContentScope.PokemonDetailsScreen(
    loading: Boolean,
    pokemonSet: List<Pokemon>,
    pokemon: Pokemon,
    evolutions: List<PokemonDetailsEvolutions>,
    moves: List<PokemonDetailsMoves>,
    isFavorite: Boolean = false,
    onPage: (Pokemon) -> Unit = {},
    onFavoriteClick: (Int) -> Unit = { _ -> },
    onBackClick: () -> Unit = {},
) {
    val density = LocalDensity.current

    val pagerState = rememberPagerState(initialPage = pokemon.id - 1) {
        pokemonSet.size
    }

    val swipeableState = rememberSwipeableState(initialValue = 1)
    val topAnchorMin = with(density) { (16 + 16 + 48).dp.toPx() }
    val topAnchorMax = with(density) { 324.dp.toPx() }

    val anchors = mapOf(topAnchorMin to 0, topAnchorMax to 1)
    val swipeableProgress by remember {
        derivedStateOf {
            swipeableState.progress
        }
    }

    val scaleTarget by remember {
        derivedStateOf {
            if (swipeableProgress.to == 1) {
                if (swipeableProgress.fraction > 0.7f) {
                    swipeableProgress.fraction
                } else {
                    0f
                }
            } else {
                1f - swipeableProgress.fraction
            }
        }
    }
    val scaleModifier = Modifier.graphicsLayer {
        scaleX = scaleTarget
        scaleY = scaleTarget
    }

    val textAlphaTarget by remember {
        derivedStateOf {
            if (swipeableProgress.to == 1) {
                swipeableProgress.fraction
            } else {
                1f - (swipeableProgress.fraction)
            }
        }
    }

    val imageAlphaTarget by remember {
        derivedStateOf {
            if (swipeableProgress.to == 1) {
                if (swipeableProgress.fraction > 0.6f) {
                    swipeableProgress.fraction
                } else {
                    0f
                }
            } else {
                1f - (swipeableProgress.fraction * 4f)
            }
        }
    }


    val cardPaddingTarget by remember {
        derivedStateOf {
            val max = with(density) { 40.dp.toPx() }
            val min = max / 4

            val resolvedValue = if (swipeableProgress.to == 1) {
                swipeableProgress.fraction * max
            } else {
                (1 - swipeableProgress.fraction) * max
            }

            resolvedValue
                .coerceIn(min, max)
                .roundToInt()
        }
    }

    val pagerZIndex by remember {
        derivedStateOf {
            if (swipeableProgress.from == 0 && swipeableProgress.to == 0) {
                -1f
            } else {
               0f
            }
        }
    }

    LaunchedEffect(pagerState, pokemonSet) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            if (pokemonSet.isNotEmpty()) {
                onPage(pokemonSet[page])
            }
        }
    }

    PokemonTypesTheme(
        types = pokemon.typeOfPokemon
    ) {
        val pokemonTypeColor by animateColorAsState(
            targetValue = PokemonTypesTheme.colorScheme.surface,
            animationSpec = tween(durationMillis = 500),
            label = "pokemonTypeSurfaceColor"
        )

        Surface(
            modifier = Modifier
                .drawBehind {
                    drawRect(pokemonTypeColor)
                },
            color = Color.Transparent
        ) {
            Box(Modifier.fillMaxSize()) {
                RoundedRectangleDecoration(
                    Modifier
                        .offset(x = (-60).dp, y = (-50).dp)
                        .rotate(-20f)
                )
                DottedDecoration(
                    Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 4.dp, end = 100.dp)
                )
                RotatingPokeBall(
                    Modifier
                        .align(Alignment.TopCenter)
                        .statusBarsPadding()
                        .padding(top = 16.dp)
                        .padding(top = 164.dp)
                        .size(180.dp)
                        .graphicsLayer { alpha = textAlphaTarget },
                    tint = PokemonTypesTheme.colorScheme.surfaceVariant
                )
                Box(
                    Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .padding(top = 16.dp)
                ) {
                    val textFadeInTransition = fadeIn(tween(durationMillis = 210, delayMillis = 90, easing = LinearOutSlowInEasing))
                    val textFadeOutTransition = fadeOut(tween(durationMillis = 90, easing = FastOutLinearInEasing))

                    AnimatedContent(
                        modifier = Modifier
                            .padding(top = 24.dp)
                            .graphicsLayer { alpha = textAlphaTarget },
                        targetState = pokemon,
                        transitionSpec = {
                            if (initialState.id < targetState.id) {
                                textFadeInTransition +
                                    slideInHorizontally(initialOffsetX = { with(density) { 16.dp.roundToPx() } }, animationSpec = tween(300)) with
                                textFadeOutTransition
                            } else {
                                textFadeInTransition +
                                    slideInHorizontally(initialOffsetX = { with(density) { -16.dp.roundToPx() } }, animationSpec = tween(300)) with
                                textFadeOutTransition
                            }.using(SizeTransform(clip = false))
                        },
                        label = "headerTransition"
                    ) { targetPokemon ->
                        Header(pokemon = targetPokemon)
                    }

                    val nestedScrollConnection = nestedScrollConnection(
                        swipeableState = swipeableState,
                        minAnchor = topAnchorMin
                    )

                    Surface(
                        modifier = Modifier
                            .animateEnterExit(
                                enter = Material3Transitions.SharedYAxisEnterTransition(density),
                                exit = ExitTransition.None
                            )
                            .align(Alignment.TopCenter)
                            .swipeable(
                                state = swipeableState,
                                anchors = anchors,
                                orientation = Orientation.Vertical
                            )
                            .nestedScroll(nestedScrollConnection)
                            .offset {
                                IntOffset(
                                    x = 0,
                                    y = swipeableState.offset.value.roundToInt()
                                )
                            },
                        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                    ) {
                        CardContent(
                            pokemon = pokemon,
                            evolutions = evolutions,
                            moves = moves,
                            modifier = Modifier.offset { IntOffset(x = 0, y = cardPaddingTarget) },
                        )
                    }

                    PokemonPager(
                        modifier = Modifier
                            .zIndex(pagerZIndex)
                            .padding(top = 124.dp)
                            .graphicsLayer { alpha = imageAlphaTarget },
                        loading = loading,
                        pokemonList = pokemonSet,
                        backgroundColor = PokemonTypesTheme.colorScheme.surface,
                        enabled = swipeableState.currentValue == 1,
                        pagerState = pagerState,
                    ) { it, progress, tint ->
                        PagerPokemonImage(
                            image = it.image,
                            description = it.name,
                            tint = tint,
                            progress = progress,
                            modifier = scaleModifier.size(240.dp),
                        )
                    }
                }
                NavigationTopAppBar(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(top = 16.dp)
                    ,
                    title = {
                        Text(
                            text = pokemon.name,
                            modifier = Modifier.graphicsLayer {
                                // TODO: Look into collapsing toolbar behavior later
                                alpha = 1f - textAlphaTarget
                            }
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = { onFavoriteClick(pokemon.id) }
                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = if (isFavorite) "Remove from Favorites" else "Add to Favorites",
                            )
                        }
                    },
                    onBackClick = onBackClick
                )
            }
        }
    }
}

private enum class Sections(val title: String) {
    About("About"),
    BaseStats("Base stats"),
    Evolution("Evolution"),
    Moves("Moves")
}

@Composable
private fun CardContent(
    modifier: Modifier,
    pokemon: Pokemon,
    evolutions: List<PokemonDetailsEvolutions>,
    moves: List<PokemonDetailsMoves>,
) {
    val sectionTitles = Sections.entries.map { it.title }
    var section by rememberSaveable { mutableStateOf(Sections.BaseStats) }

    val tabIndicatorColor by animateColorAsState(
        targetValue = PokemonTypesTheme.colorScheme.primary,
        tween(durationMillis = 500),
        label = "tabIndicatorColor"
    )

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        PokemonTypesTheme(types = pokemon.typeOfPokemon) {
            TabRow(
                containerColor = MaterialTheme.colorScheme.surface,
                selectedTabIndex = section.ordinal,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[section.ordinal])
                            .clip(RoundedCornerShape(100)),
                        color = tabIndicatorColor
                    )
                },
            ) {
                sectionTitles.forEachIndexed { index, text ->
                    val active = index == section.ordinal
                    Tab(
                        selected = active,
                        selectedContentColor = PokemonTypesTheme.colorScheme.primary,
                        unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        onClick = { section = Sections.entries.toTypedArray()[index] },
                    ) {
                        Text(
                            text = text,
                            fontWeight = if (active) FontWeight.Medium else FontWeight.Normal,
                            modifier = Modifier.padding(vertical = 20.dp)
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier.padding(24.dp)
        ) {
            when (section) {
                Sections.About -> AboutSection(pokemon)
                Sections.BaseStats -> BaseStatsSection(pokemon)
                Sections.Evolution -> EvolutionSection(evolutions = evolutions)
                else -> MovesSection(moves = moves)
            }
        }
    }
}

@Composable
private fun RoundedRectangleDecoration(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(150.dp)
            .background(color = Color(0x22FFFFFF), shape = RoundedCornerShape(32.dp))
    )
}

@Composable
private fun DottedDecoration(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.dotted),
        contentDescription = null,
        modifier = modifier.size(width = 63.dp, height = 34.dp),
        alpha = 0.3f
    )
}

@Composable
private fun Header(
    modifier: Modifier = Modifier,
    pokemon: Pokemon
) {
    Column(
        modifier.padding(top = 40.dp, bottom = 32.dp, start = 24.dp, end = 24.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = pokemon.name,
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.alignByBaseline()
            )
            Text(
                text = formatId(pokemon.id),
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier
                    .alignByBaseline()
                    .graphicsLayer { alpha = Emphasis.Medium.alpha }
            )
        }
        Spacer(Modifier.height(8.dp))
        Row(
            Modifier.fillMaxWidth(),
        ) {
            PokemonTypeLabels(
                types = pokemon.typeOfPokemon,
                metrics = MEDIUM,
            )
        }
    }
}

@Composable
private fun RotatingPokeBall(
    modifier: Modifier = Modifier,
    tint: Color = Color(0x40F5F5F5)
) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing)
        ),
        label = "rotatingAngle"
    )

    PokeBall(
        modifier = modifier
            .graphicsLayer {
                rotationZ = angle
            },
        tint = tint,
    )
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalTransitionApi::class)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview
@Composable
private fun PokemonDetailsPreview() {
    var activePokemon by remember { mutableStateOf(SamplePokemonData.first { it.name == "Pikachu" }) }

    AppTheme {
        Surface(Modifier.fillMaxSize()) {
            AnimatedContent(
                targetState = true,
                label = ""
            ) { targetState ->
                PokemonDetailsScreen(
                    loading = !targetState,
                    pokemonSet = SamplePokemonData,
                    pokemon = activePokemon,
                    evolutions = mapSampleEvolutionsToList(
                        activePokemon.evolutionChain
                    ),
                    moves = mapSampleMovesToDetailsList(),
                    onPage = {
                        activePokemon = it
                    })
            }
        }
    }
}