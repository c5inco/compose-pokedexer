package des.c5inco.pokedexer.ui.pokedex

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.materialkolor.PaletteStyle
import des.c5inco.pokedexer.R
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.data.pokemon.mapSampleEvolutionsToList
import des.c5inco.pokedexer.data.pokemon.mapSampleMovesToDetailsList
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.ui.common.Emphasis
import des.c5inco.pokedexer.ui.common.Material3Transitions
import des.c5inco.pokedexer.ui.common.NavigationTopAppBar
import des.c5inco.pokedexer.ui.common.Pokeball
import des.c5inco.pokedexer.ui.common.PokemonTypeLabels
import des.c5inco.pokedexer.ui.common.TypeLabelMetrics.Companion.MEDIUM
import des.c5inco.pokedexer.ui.common.consumeSwipeNestedScrollConnection
import des.c5inco.pokedexer.ui.common.formatId
import des.c5inco.pokedexer.ui.pokedex.section.AboutSection
import des.c5inco.pokedexer.ui.pokedex.section.BaseStatsSection
import des.c5inco.pokedexer.ui.pokedex.section.EvolutionSection
import des.c5inco.pokedexer.ui.pokedex.section.MovesSection
import des.c5inco.pokedexer.ui.theme.AppPaletteStyles
import des.c5inco.pokedexer.ui.theme.AppTheme
import des.c5inco.pokedexer.ui.theme.PokemonTypeColorOverlay
import des.c5inco.pokedexer.ui.theme.PokemonTypesTheme
import kotlin.math.roundToInt

@Composable
fun AnimatedContentScope.PokemonDetailsScreenRoute(
    viewModel: PokedexViewModel,
    detailsViewModel: PokemonDetailsViewModel,
    onBackClick: (Int) -> Unit,
) {
    PokemonTypesTheme(
        types = detailsViewModel.details.typeOfPokemon
    ) {
        PokemonDetailsScreen(
            loading = viewModel.uiState.loading,
            pokemonSet = viewModel.uiState.pokemon,
            pokemon = detailsViewModel.details,
            evolutions = detailsViewModel.evolutions,
            moves = detailsViewModel.moves,
            abilities = detailsViewModel.abilities,
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
}

enum class DragValue { Start, Center, End }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimatedContentScope.PokemonDetailsScreen(
    loading: Boolean,
    pokemonSet: List<Pokemon>,
    pokemon: Pokemon,
    evolutions: List<PokemonDetailsEvolutions>,
    moves: List<PokemonDetailsMoves>,
    abilities: List<PokemonDetailsAbilities>,
    isFavorite: Boolean = false,
    onPage: (Pokemon) -> Unit = {},
    onFavoriteClick: (Int) -> Unit = { _ -> },
    onBackClick: (Int) -> Unit = {},
) {
    val density = LocalDensity.current

    val pagerState = rememberPagerState(initialPage = pokemon.id - 1) {
        pokemonSet.size
    }

    val draggableAnchors = with(density) {
        DraggableAnchors {
            DragValue.Start at 324.dp.toPx()
            DragValue.End at (16 + 16 + 48).dp.toPx()
        }
    }

    val defaultDecayAnimationSpec = rememberSplineBasedDecay<Float>()
    val anchorDraggableState = remember {
        AnchoredDraggableState(
            initialValue = DragValue.Start,
            anchors = draggableAnchors,
            positionalThreshold = { distance -> distance * 0.5f },
            velocityThreshold = { with(density) { 100.dp.toPx() } },
            snapAnimationSpec = spring(),
            decayAnimationSpec = defaultDecayAnimationSpec
        )
    }
    val anchorDraggableProgress by remember {
        derivedStateOf {
            anchorDraggableState.progress(DragValue.Start, DragValue.End)
        }
    }

    val scaleTarget by remember {
        derivedStateOf {
            if (anchorDraggableProgress < 0.7f) {
                1f - anchorDraggableProgress
            } else {
                0f
            }
        }
    }
    val scaleModifier = Modifier.graphicsLayer {
        scaleX = scaleTarget
        scaleY = scaleTarget
    }

    val textAlphaTarget by remember {
        derivedStateOf {
            1f - anchorDraggableProgress
        }
    }

    val imageAlphaTarget by remember {
        derivedStateOf {
            1f - anchorDraggableProgress * 4f
        }
    }


    val cardPaddingTarget by remember {
        derivedStateOf {
            val max = with(density) { 40.dp.toPx() }
            val min = max / 4

            val resolvedValue = (1f - anchorDraggableProgress) * max

            resolvedValue
                .coerceIn(min, max)
                .roundToInt()
        }
    }

    val pagerZIndex by remember {
        derivedStateOf {
            if (anchorDraggableProgress < 1f) {
                0f
            } else {
                -1f
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
                    .padding(top = 140.dp)
                    .size(240.dp)
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
                        (textFadeInTransition +
                            slideInHorizontally(
                                initialOffsetX = {
                                    val offset = if (initialState.id < targetState.id) 16 else -16
                                    with(density) { offset.dp.roundToPx() }
                                },
                                animationSpec = tween(300)
                            ))
                        .togetherWith(
                            textFadeOutTransition
                        ).using(SizeTransform(clip = false))
                    },
                    label = "headerTransition"
                ) { targetPokemon ->
                    Header(pokemon = targetPokemon)
                }

                val nestedScrollConnection = consumeSwipeNestedScrollConnection(
                    state = anchorDraggableState,
                    orientation = Orientation.Vertical
                )

                Surface(
                    modifier = Modifier
                        .animateEnterExit(
                            enter = Material3Transitions.SharedYAxisEnterTransition(density),
                            exit = ExitTransition.None
                        )
                        .align(Alignment.TopCenter)
                        .nestedScroll(nestedScrollConnection)
                        .offset {
                            IntOffset(
                                x = 0,
                                y = anchorDraggableState
                                    .requireOffset()
                                    .roundToInt()
                            )
                        }
                        .anchoredDraggable(anchorDraggableState, Orientation.Vertical),
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                ) {
                    CardContent(
                        pokemon = pokemon,
                        evolutions = evolutions,
                        moves = moves,
                        abilities = abilities,
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
                    enabled = anchorDraggableState.currentValue == DragValue.Start,
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
                            alpha = 1f - (textAlphaTarget * 2.5f)
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
                onBackClick = { onBackClick(pokemon.id) }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CardContent(
    modifier: Modifier,
    pokemon: Pokemon,
    evolutions: List<PokemonDetailsEvolutions>,
    moves: List<PokemonDetailsMoves>,
    abilities: List<PokemonDetailsAbilities>,
) {
    val sectionTitles = Sections.entries.map { it.title }
    var section by rememberSaveable { mutableStateOf(Sections.BaseStats) }

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        val tabIndicatorColor by animateColorAsState(
            targetValue = PokemonTypesTheme.colorScheme.primary,
            animationSpec = tween(durationMillis = 500),
            label = "tabIndicatorColor"
        )

        SecondaryTabRow(
            containerColor = MaterialTheme.colorScheme.surface,
            selectedTabIndex = section.ordinal,
            indicator = {
                SecondaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset(section.ordinal)
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

        Box(
            modifier = Modifier.padding(24.dp)
        ) {
            when (section) {
                Sections.About -> AboutSection(pokemon, abilities)
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
    val infiniteTransition = rememberInfiniteTransition(label = "rotatingPokeball")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing)
        ),
        label = "rotatingAngle"
    )

    Pokeball(
        tint = tint,
        modifier = modifier.graphicsLayer { rotationZ = angle },
    )
}

@PreviewLightDark
@Composable
private fun PokemonDetailsPreview(
    @PreviewParameter(PokemonPreviewProvider::class) pokemon: Pokemon,
) {
    var activePokemon by remember { mutableStateOf(pokemon) }

    AppTheme {
        Surface {
            AnimatedContent(
                targetState = true,
                label = ""
            ) { targetState ->
                PokemonTypesTheme(
                    types = activePokemon.typeOfPokemon
                ) {
                    PokemonDetailsScreen(
                        loading = !targetState,
                        pokemonSet = SamplePokemonData,
                        pokemon = activePokemon,
                        evolutions = mapSampleEvolutionsToList(
                            activePokemon.evolutionChain
                        ),
                        moves = mapSampleMovesToDetailsList(),
                        abilities = listOf(),
                        onPage = {
                            activePokemon = it
                        }
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PokemonDetailsPalettePreview(
    @PreviewParameter(PaletteStyleProvider::class) paletteStyle: PaletteStyle
) {
    val activePokemon = SamplePokemonData.first()

    AppTheme {
        Surface {
            AnimatedContent(
                targetState = true,
                label = ""
            ) { targetState ->
                PokemonTypeColorOverlay(
                    types = activePokemon.typeOfPokemon,
                    paletteStyle = paletteStyle
                ) {
                    PokemonDetailsScreen(
                        loading = !targetState,
                        pokemonSet = SamplePokemonData,
                        pokemon = activePokemon,
                        evolutions = mapSampleEvolutionsToList(
                            activePokemon.evolutionChain
                        ),
                        moves = mapSampleMovesToDetailsList(),
                        abilities = listOf()
                    )
                }
            }
        }
    }
}

class PokemonPreviewProvider : PreviewParameterProvider<Pokemon> {
    override val values = sequenceOf(
        SamplePokemonData[0],
        SamplePokemonData[3],
        SamplePokemonData[6],
        SamplePokemonData.last(),
    )
}

class PaletteStyleProvider : PreviewParameterProvider<PaletteStyle> {
    override val values = AppPaletteStyles.asSequence()
}