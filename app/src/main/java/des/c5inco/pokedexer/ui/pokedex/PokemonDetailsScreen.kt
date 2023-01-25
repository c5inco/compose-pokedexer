package des.c5inco.pokedexer.ui.pokedex

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import des.c5inco.pokedexer.R
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.data.pokemon.mapSampleEvolutionsToList
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.model.color
import des.c5inco.pokedexer.ui.common.NavigationTopAppBar
import des.c5inco.pokedexer.ui.common.PokeBall
import des.c5inco.pokedexer.ui.common.PokemonTypeLabels
import des.c5inco.pokedexer.ui.common.TypeLabelMetrics.Companion.MEDIUM
import des.c5inco.pokedexer.ui.common.formatId
import des.c5inco.pokedexer.ui.pokedex.section.AboutSection
import des.c5inco.pokedexer.ui.pokedex.section.BaseStatsSection
import des.c5inco.pokedexer.ui.pokedex.section.EvolutionSection
import des.c5inco.pokedexer.ui.pokedex.section.MovesSection
import des.c5inco.pokedexer.ui.theme.PokedexerTheme
import kotlin.math.roundToInt

@Composable
fun PokemonDetailsScreenRoute(
    viewModel: PokedexViewModel,
    detailsViewModel: PokemonDetailsViewModel,
    onBackClick: () -> Unit,
) {
    PokemonDetailsScreen(
        loading = viewModel.uiState.loading,
        pokemonSet = viewModel.uiState.pokemon,
        pokemon = detailsViewModel.details,
        evolutions = detailsViewModel.evolutions,
        onPage = {
            detailsViewModel.refresh(it)
        },
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
internal fun PokemonDetailsScreen(
    loading: Boolean,
    pokemonSet: List<Pokemon>,
    pokemon: Pokemon,
    evolutions: List<PokemonDetailsEvolutions>,
    onPage: (Pokemon) -> Unit = {},
    onBackClick: () -> Unit = {},
    onFavoriteClick: (Int) -> Unit = { _ -> }
) {
    val pagerState = rememberPagerState(initialPage = pokemon.id - 1)
    val pokemonTypeColor = remember { Animatable(pokemon.color()) }

    val swipeableState = rememberSwipeableState(initialValue = 1)
    val topAnchorMin = with(LocalDensity.current) { (16 + 16 + 48).dp.toPx() }
    val topAnchorMax = with(LocalDensity.current) { 300.dp.toPx() }

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
                val incomingPokemon = pokemonSet[page]
                onPage(incomingPokemon)
                pokemonTypeColor.animateTo(
                    targetValue = incomingPokemon.color(),
                    animationSpec = tween(durationMillis = 500)
                )
            }
        }
    }

    Surface(
        modifier = Modifier.drawBehind {
            drawRect(pokemonTypeColor.value)
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
                    .padding(top = 150.dp)
                    .size(180.dp)
                    .graphicsLayer { alpha = textAlphaTarget }
            )
            Box(
                Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(top = 16.dp)
                    .swipeable(
                        state = swipeableState,
                        anchors = anchors,
                        orientation = Orientation.Vertical
                    )
            ) {
                HeaderLeft(
                    pokemon = pokemon,
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .graphicsLayer { alpha = textAlphaTarget }
                )
                HeaderRight(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 24.dp)
                        .graphicsLayer { alpha = textAlphaTarget },
                    pokemon = pokemon
                )
                CardContent(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset { IntOffset(x = 0, y = swipeableState.offset.value.roundToInt()) },
                    pokemon = pokemon,
                    evolutions = evolutions,
                )

                PokemonPager(
                    modifier = Modifier
                        .zIndex(pagerZIndex)
                        .padding(top = 116.dp)
                        .graphicsLayer { alpha = imageAlphaTarget }
                    ,
                    loading = loading,
                    pokemonList = pokemonSet,
                    backgroundColor = pokemon.color(),
                    enabled = swipeableState.currentValue == 1,
                    pagerState = pagerState,
                ) { it, progress, tint ->
                    PagerPokemonImage(
                        image = it.image,
                        description = it.name,
                        tint = tint,
                        progress = progress,
                        modifier = scaleModifier.size(216.dp),
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
                        modifier = Modifier,
                        onClick = { onFavoriteClick(pokemon.id) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite this Pokemon",
                            tint = Color.White
                        )
                    }
                },
                contentColor = Color.White,
                onBackClick = onBackClick
            )
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
    evolutions: List<PokemonDetailsEvolutions>
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
    ) {
        Column(
            Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            val sectionTitles = Sections.values().map { it.title }
            var section by remember { mutableStateOf(Sections.BaseStats) }
            TabRow(
                backgroundColor = Color.Transparent,
                selectedTabIndex = section.ordinal,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[section.ordinal])
                            .clip(RoundedCornerShape(100)),
                        color = MaterialTheme.colors.primary
                    )
                },
            ) {
                sectionTitles.forEachIndexed { index, text ->
                    val active = index == section.ordinal
                    Tab(
                        selected = active,
                        onClick = { section = Sections.values()[index] },
                    ) {
                        Text(
                            text = text,
                            fontWeight = if (active) FontWeight.Medium else FontWeight.Normal,
                            modifier = Modifier.padding(vertical = 20.dp)
                        )
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
                    else -> MovesSection(pokemon)
                }
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
private fun HeaderLeft(
    modifier: Modifier = Modifier,
    pokemon: Pokemon
) {
    Column(
        modifier.padding(top = 40.dp, bottom = 32.dp, start = 24.dp, end = 24.dp)
    ) {
        Text(
            text = pokemon.name,
            style = MaterialTheme.typography.h4,
            color = Color.White
        )
        Spacer(Modifier.height(8.dp))
        pokemon.typeOfPokemon.let {
            Row {
                PokemonTypeLabels(it, MEDIUM)
            }
        }
    }
}

@Composable
private fun HeaderRight(
    modifier: Modifier = Modifier,
    pokemon: Pokemon
) {
    Column(
        modifier.padding(top = 52.dp, bottom = 32.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = formatId(pokemon.id),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = pokemon.category,
            fontSize = 12.sp,
            color = Color.White
        )
    }
}

@Composable
private fun RotatingPokeBall(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 360f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing)
        )
    )

    PokeBall(
        modifier = modifier
            .graphicsLayer {
                rotationZ = angle
            },
        tint = Color(0xffF5F5F5),
        alpha = 0.25f
    )
}

@Preview
@Composable
private fun PokemonDetailsPreview() {
    var activePokemon by remember { mutableStateOf(SamplePokemonData[2]) }

    PokedexerTheme {
        Surface(Modifier.fillMaxSize()) {
            PokemonDetailsScreen(
                loading = false,
                pokemonSet = SamplePokemonData,
                pokemon = activePokemon,
                evolutions = mapSampleEvolutionsToList(
                    activePokemon.evolutionChain
                ),
                onPage = {
                    activePokemon = it
                })
        }
    }
}