package des.c5inco.pokedexer.ui.pokedex

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableDefaults
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.materialkolor.PaletteStyle
import des.c5inco.pokedexer.R
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.data.pokemon.mapSampleAbilitiesToDetailsList
import des.c5inco.pokedexer.data.pokemon.mapSampleEvolutionsToList
import des.c5inco.pokedexer.data.pokemon.mapSampleMovesToDetailsList
import des.c5inco.pokedexer.shared.model.Pokemon
import des.c5inco.pokedexer.shared.model.mapTypeToCuratedAnalogousHue
import des.c5inco.pokedexer.ui.common.Emphasis
import des.c5inco.pokedexer.ui.common.NavigationTopAppBar
import des.c5inco.pokedexer.ui.common.Pokeball
import des.c5inco.pokedexer.ui.common.PokemonTypeLabels
import des.c5inco.pokedexer.ui.common.TypeLabelMetrics.Companion.MEDIUM
import des.c5inco.pokedexer.ui.common.calculateAnalogousColors
import des.c5inco.pokedexer.ui.common.consumeSwipeNestedScrollConnection
import des.c5inco.pokedexer.ui.common.formatId
import des.c5inco.pokedexer.ui.common.meshGradient
import des.c5inco.pokedexer.ui.pokedex.section.AboutSection
import des.c5inco.pokedexer.ui.pokedex.section.BaseStatsSection
import des.c5inco.pokedexer.ui.pokedex.section.EvolutionSection
import des.c5inco.pokedexer.ui.pokedex.section.MovesSection
import des.c5inco.pokedexer.ui.theme.AppPaletteStyles
import des.c5inco.pokedexer.ui.theme.AppTheme
import des.c5inco.pokedexer.ui.theme.PokemonTypeColorOverlay
import des.c5inco.pokedexer.ui.theme.PokemonTypesTheme
import kotlin.math.roundToInt

private const val COLLAPSED_CARD_ANCHOR_DP = 80
private const val IMAGE_SCALE_VISIBLE_PROGRESS = 0.7f
private const val IMAGE_ALPHA_PROGRESS_MULTIPLIER = 4f
private const val CARD_PADDING_DIVISOR = 4
private const val ANALOGOUS_HUE_OFFSET_DEGREES = 24f
private const val GRADIENT_FIRST_THIRD = 0.33f
private const val GRADIENT_SECOND_THIRD = 0.66f
private const val GRADIENT_LOWER_ROW_Y = 0.6f
private const val GRADIENT_INNER_LEFT_X = 0.25f
private const val GRADIENT_INNER_LEFT_Y = 0.4f
private const val GRADIENT_INNER_RIGHT_X = 0.8f
private const val GRADIENT_MIDDLE_Y = 0.5f
private const val GRADIENT_FINAL_SECOND_THIRD = 0.67f
private const val DECORATION_ROTATION_DEGREES = -20f
private const val HEADER_SLIDE_DURATION_MILLIS = 300
private const val COLLAPSING_TITLE_ALPHA_MULTIPLIER = 2.5f
private const val FULLY_ROUNDED_CORNER_PERCENT = 100
private const val ROUNDED_RECTANGLE_COLOR = 0x22FFFFFF
private const val DEFAULT_POKEBALL_TINT = 0x40F5F5F5
private const val CHARMANDER_SAMPLE_INDEX = 3
private const val SQUIRTLE_SAMPLE_INDEX = 6

@Composable
fun PokemonDetailsScreenRoute(
    detailsViewModel: PokemonDetailsViewModel,
    onBackClick: (Int) -> Unit,
) {
    val pokemonSet by
        detailsViewModel.pokemonSet.collectAsStateWithLifecycle(initialValue = emptyList())
    val uiState by detailsViewModel.uiState.collectAsStateWithLifecycle()
    val currentState = uiState

    if (pokemonSet.isNotEmpty() && currentState != null) {
        PokemonTypesTheme(types = currentState.details.typeOfPokemon) {
            PokemonDetailsScreen(
                pokemonSet = pokemonSet,
                uiState = currentState,
                onPage = { detailsViewModel.refresh(it) },
                onFavoriteClick = { detailsViewModel.toggleFavorite(it) },
                onBackClick = onBackClick,
            )
        }
    }
}

enum class DragValue {
    Start,
    Center,
    End,
}

@Composable
fun PokemonDetailsScreen(
    pokemonSet: List<Pokemon>,
    uiState: PokemonDetailsUiState,
    onPage: (Pokemon) -> Unit = {},
    onFavoriteClick: (Int) -> Unit = {},
    onBackClick: (Int) -> Unit = {},
) {
    PokemonDetailsLayout.Render(
        pokemonSet = pokemonSet,
        state = uiState,
        onPage = onPage,
        onFavoriteClick = onFavoriteClick,
        onBackClick = onBackClick,
    )
}

private object PokemonDetailsLayout {
    private data class PokemonDetailsCallbacks(
        val onPage: (Pokemon) -> Unit,
        val onFavoriteClick: (Int) -> Unit,
        val onBackClick: (Int) -> Unit,
    )

    private data class PokemonDetailsInteractionState(
        val pagerState: PagerState,
        val anchorDraggableState: AnchoredDraggableState<DragValue>,
        val scaleTarget: Float,
        val textAlphaTarget: Float,
        val imageAlphaTarget: Float,
        val cardPaddingTarget: Int,
        val pagerZIndex: Float,
    )

    private data class PokemonDetailsColors(
        val typeSurface: Color,
        val gradient: List<List<Pair<Offset, Color>>>,
    )

    @Composable
    fun Render(
        pokemonSet: List<Pokemon>,
        state: PokemonDetailsUiState,
        onPage: (Pokemon) -> Unit,
        onFavoriteClick: (Int) -> Unit,
        onBackClick: (Int) -> Unit,
    ) {
        val callbacks = PokemonDetailsCallbacks(onPage, onFavoriteClick, onBackClick)
        val interactionState = rememberPokemonDetailsInteractionState(pokemonSet, state.details)
        ObservePokemonPages(interactionState.pagerState, pokemonSet, callbacks.onPage)
        val colors = rememberPokemonDetailsColors()

        PokemonDetailsSurface(
            pokemonSet = pokemonSet,
            state = state,
            callbacks = callbacks,
            interactionState = interactionState,
            colors = colors,
        )
    }

    @Composable
    private fun rememberPokemonDetailsInteractionState(
        pokemonSet: List<Pokemon>,
        pokemon: Pokemon,
    ): PokemonDetailsInteractionState {
        val density = LocalDensity.current
        val initialPage = remember {
            pokemonDetailsInitialPage(pokemonSet = pokemonSet, displayedPokemonId = pokemon.id)
        }
        val pagerState = rememberPagerState(initialPage = initialPage) { pokemonSet.size }
        val draggableAnchors =
            with(density) {
                DraggableAnchors {
                    DragValue.Start at 324.dp.toPx()
                    DragValue.End at COLLAPSED_CARD_ANCHOR_DP.dp.toPx()
                }
            }
        val anchorDraggableState = remember {
            AnchoredDraggableState(initialValue = DragValue.Start, anchors = draggableAnchors)
        }
        val anchorDraggableProgress by remember {
            derivedStateOf { anchorDraggableState.progress(DragValue.Start, DragValue.End) }
        }

        val scaleTarget by remember {
            derivedStateOf {
                if (anchorDraggableProgress < IMAGE_SCALE_VISIBLE_PROGRESS) {
                    1f - anchorDraggableProgress
                } else {
                    0f
                }
            }
        }
        val textAlphaTarget by remember { derivedStateOf { 1f - anchorDraggableProgress } }
        val imageAlphaTarget by remember {
            derivedStateOf { 1f - anchorDraggableProgress * IMAGE_ALPHA_PROGRESS_MULTIPLIER }
        }
        val cardPaddingTarget by remember {
            derivedStateOf {
                val max = with(density) { 40.dp.toPx() }
                val min = max / CARD_PADDING_DIVISOR
                val resolvedValue = (1f - anchorDraggableProgress) * max
                resolvedValue.coerceIn(min, max).roundToInt()
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

        return PokemonDetailsInteractionState(
            pagerState = pagerState,
            anchorDraggableState = anchorDraggableState,
            scaleTarget = scaleTarget,
            textAlphaTarget = textAlphaTarget,
            imageAlphaTarget = imageAlphaTarget,
            cardPaddingTarget = cardPaddingTarget,
            pagerZIndex = pagerZIndex,
        )
    }

    @Composable
    private fun ObservePokemonPages(
        pagerState: PagerState,
        pokemonSet: List<Pokemon>,
        onPage: (Pokemon) -> Unit,
    ) {
        LaunchedEffect(pagerState, pokemonSet) {
            snapshotFlow { pagerState.currentPage }
                .collect { page ->
                    pokemonDetailsPokemonForPage(pokemonSet = pokemonSet, page = page)?.let(onPage)
                }
        }
    }

    @Composable
    private fun rememberPokemonDetailsColors(): PokemonDetailsColors {
        val pokemonTypeSurfaceColor = PokemonTypesTheme.colorScheme.surface
        val hueIndex = mapTypeToCuratedAnalogousHue(PokemonTypesTheme.colorScheme.type)
        val analogousSurfaceColor =
            remember(pokemonTypeSurfaceColor) {
                calculateAnalogousColors(pokemonTypeSurfaceColor, ANALOGOUS_HUE_OFFSET_DEGREES)[
                    hueIndex]
            }
        val gradient =
            listOf(
                listOf(
                    Offset(0f, 0f) to analogousSurfaceColor,
                    Offset(GRADIENT_FIRST_THIRD, 0f) to analogousSurfaceColor,
                    Offset(GRADIENT_SECOND_THIRD, 0f) to analogousSurfaceColor,
                    Offset(1f, 0f) to analogousSurfaceColor,
                ),
                listOf(
                    Offset(0f, GRADIENT_LOWER_ROW_Y) to pokemonTypeSurfaceColor,
                    Offset(GRADIENT_INNER_LEFT_X, GRADIENT_INNER_LEFT_Y) to pokemonTypeSurfaceColor,
                    Offset(GRADIENT_INNER_RIGHT_X, GRADIENT_LOWER_ROW_Y) to pokemonTypeSurfaceColor,
                    Offset(1f, GRADIENT_MIDDLE_Y) to pokemonTypeSurfaceColor,
                ),
                listOf(
                    Offset(0f, 1f) to PokemonTypesTheme.colorScheme.primary,
                    Offset(GRADIENT_FIRST_THIRD, 1f) to PokemonTypesTheme.colorScheme.primary,
                    Offset(GRADIENT_FINAL_SECOND_THIRD, 1f) to
                        PokemonTypesTheme.colorScheme.primary,
                    Offset(1f, 1f) to PokemonTypesTheme.colorScheme.primary,
                ),
            )

        return PokemonDetailsColors(typeSurface = pokemonTypeSurfaceColor, gradient = gradient)
    }

    @Composable
    private fun PokemonDetailsSurface(
        pokemonSet: List<Pokemon>,
        state: PokemonDetailsUiState,
        callbacks: PokemonDetailsCallbacks,
        interactionState: PokemonDetailsInteractionState,
        colors: PokemonDetailsColors,
    ) {
        Surface(
            modifier =
                Modifier.meshGradient(points = colors.gradient, resolutionX = 32, resolutionY = 32),
            color = Color.Transparent,
        ) {
            Box(Modifier.fillMaxSize()) {
                RoundedRectangleDecoration(
                    Modifier.offset(x = (-60).dp, y = (-50).dp).rotate(DECORATION_ROTATION_DEGREES)
                )
                DottedDecoration(Modifier.align(Alignment.TopEnd).padding(top = 4.dp, end = 100.dp))
                RotatingPokeBall(
                    Modifier.align(Alignment.TopCenter)
                        .statusBarsPadding()
                        .padding(top = 16.dp)
                        .padding(top = 140.dp)
                        .size(240.dp)
                        .graphicsLayer { alpha = interactionState.textAlphaTarget },
                    tint = PokemonTypesTheme.colorScheme.onSurface.copy(alpha = 0.15f),
                )
                PokemonDetailsContent(pokemonSet, state, interactionState, colors)
                PokemonDetailsTopAppBar(
                    state = state,
                    callbacks = callbacks,
                    textAlphaTarget = interactionState.textAlphaTarget,
                )
            }
        }
    }

    @Composable
    private fun PokemonDetailsContent(
        pokemonSet: List<Pokemon>,
        state: PokemonDetailsUiState,
        interactionState: PokemonDetailsInteractionState,
        colors: PokemonDetailsColors,
    ) {
        Box(Modifier.fillMaxSize().statusBarsPadding().padding(top = 16.dp)) {
            AnimatedPokemonHeader(state.details, interactionState.textAlphaTarget)
            PokemonDetailsCard(state, interactionState)
            PokemonDetailsPager(pokemonSet, interactionState, colors.typeSurface)
        }
    }

    @Composable
    private fun AnimatedPokemonHeader(pokemon: Pokemon, textAlphaTarget: Float) {
        val density = LocalDensity.current
        val textFadeInTransition =
            fadeIn(tween(durationMillis = 210, delayMillis = 90, easing = LinearOutSlowInEasing))
        val textFadeOutTransition =
            fadeOut(tween(durationMillis = 90, easing = FastOutLinearInEasing))

        AnimatedContent(
            modifier = Modifier.padding(top = 24.dp).graphicsLayer { alpha = textAlphaTarget },
            targetState = pokemon,
            transitionSpec = {
                (textFadeInTransition +
                        slideInHorizontally(
                            initialOffsetX = {
                                val offset = if (initialState.id < targetState.id) 16 else -16
                                with(density) { offset.dp.roundToPx() }
                            },
                            animationSpec = tween(HEADER_SLIDE_DURATION_MILLIS),
                        ))
                    .togetherWith(textFadeOutTransition)
                    .using(SizeTransform(clip = false))
            },
            label = "headerTransition",
        ) { targetPokemon ->
            Header(pokemon = targetPokemon)
        }
    }

    @Composable
    private fun BoxScope.PokemonDetailsCard(
        state: PokemonDetailsUiState,
        interactionState: PokemonDetailsInteractionState,
    ) {
        val anchorDraggableState = interactionState.anchorDraggableState
        val nestedScrollConnection =
            remember(anchorDraggableState) {
                consumeSwipeNestedScrollConnection(
                    state = anchorDraggableState,
                    orientation = Orientation.Vertical,
                )
            }

        Surface(
            modifier =
                Modifier
                    // Future work: Restore the shared-axis enter/exit transition.
                    // .animateEnterExit(
                    //     enter = Material3Transitions.SharedYAxisEnterTransition,
                    //     exit = ExitTransition.None
                    // )
                    .align(Alignment.BottomCenter)
                    .layout { measurable, constraints ->
                        val placeable =
                            measurable.measure(
                                constraints.copy(
                                    maxHeight =
                                        constraints.maxHeight -
                                            anchorDraggableState.requireOffset().roundToInt()
                                )
                            )
                        layout(placeable.width, placeable.height) { placeable.placeRelative(0, 0) }
                    }
                    .nestedScroll(nestedScrollConnection)
                    .anchoredDraggable(
                        state = anchorDraggableState,
                        orientation = Orientation.Vertical,
                        flingBehavior =
                            AnchoredDraggableDefaults.flingBehavior(anchorDraggableState),
                    ),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        ) {
            CardContent(
                pokemon = state.details,
                evolutions = state.evolutions,
                moves = state.moves,
                abilities = state.abilities,
                modifier =
                    Modifier.fillMaxHeight().offset {
                        IntOffset(x = 0, y = interactionState.cardPaddingTarget)
                    },
            )
        }
    }

    @Composable
    private fun PokemonDetailsPager(
        pokemonSet: List<Pokemon>,
        interactionState: PokemonDetailsInteractionState,
        backgroundColor: Color,
    ) {
        PokemonPager(
            modifier =
                Modifier.zIndex(interactionState.pagerZIndex).padding(top = 124.dp).graphicsLayer {
                    alpha = interactionState.imageAlphaTarget
                },
            pokemonList = pokemonSet,
            configuration =
                PokemonPagerConfiguration(
                    backgroundColor = backgroundColor,
                    foregroundColor = PokemonTypesTheme.colorScheme.onSurface,
                    enabled = interactionState.anchorDraggableState.currentValue == DragValue.Start,
                ),
            pagerState = interactionState.pagerState,
        ) { pagerPokemon, progress, tint ->
            PagerPokemonImage(
                image = pagerPokemon.image,
                description = pagerPokemon.name,
                tint = tint,
                progress = progress,
                modifier =
                    Modifier.graphicsLayer {
                            scaleX = interactionState.scaleTarget
                            scaleY = interactionState.scaleTarget
                        }
                        .size(240.dp),
            )
        }
    }

    @Composable
    private fun PokemonDetailsTopAppBar(
        state: PokemonDetailsUiState,
        callbacks: PokemonDetailsCallbacks,
        textAlphaTarget: Float,
    ) {
        NavigationTopAppBar(
            modifier = Modifier.statusBarsPadding().padding(top = 8.dp, start = 12.dp, end = 12.dp),
            title = {
                Text(
                    text = state.details.name,
                    modifier =
                        Modifier.graphicsLayer {
                            // Future work: Adopt collapsing-toolbar behavior for the title.
                            alpha = 1f - (textAlphaTarget * COLLAPSING_TITLE_ALPHA_MULTIPLIER)
                        },
                )
            },
            actions = {
                IconButton(onClick = { callbacks.onFavoriteClick(state.details.id) }) {
                    Icon(
                        imageVector =
                            if (state.isFavorite) Icons.Default.Favorite
                            else Icons.Default.FavoriteBorder,
                        contentDescription =
                            stringResource(
                                R.string.favoritesActionContentDescription,
                                if (state.isFavorite) {
                                    stringResource(R.string.removeActionContentDescription)
                                } else {
                                    stringResource(R.string.addActionContentDescription)
                                },
                            ),
                    )
                }
            },
            onBackClick = { callbacks.onBackClick(state.details.id) },
        )
    }
}

private enum class Sections(@StringRes val title: Int) {
    About(R.string.aboutLabel),
    BaseStats(R.string.baseStatsLabel),
    Evolution(R.string.evolutionLabel),
    Moves(R.string.movesLabel),
}

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

    Column(modifier = modifier) {
        val tabIndicatorColor by
            animateColorAsState(
                targetValue = PokemonTypesTheme.colorScheme.primary,
                animationSpec = tween(durationMillis = 500),
                label = "tabIndicatorColor",
            )

        SecondaryTabRow(
            containerColor = MaterialTheme.colorScheme.surface,
            selectedTabIndex = section.ordinal,
            indicator = {
                SecondaryIndicator(
                    modifier =
                        Modifier.tabIndicatorOffset(section.ordinal)
                            .clip(RoundedCornerShape(FULLY_ROUNDED_CORNER_PERCENT)),
                    color = tabIndicatorColor,
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
                        text = stringResource(text),
                        fontWeight = if (active) FontWeight.Medium else FontWeight.Normal,
                        modifier = Modifier.padding(vertical = 20.dp),
                    )
                }
            }
        }

        Box {
            when (section) {
                Sections.About -> AboutSection(pokemon = pokemon, abilities = abilities)
                Sections.BaseStats -> BaseStatsSection(pokemon = pokemon)
                Sections.Evolution -> EvolutionSection(evolutions = evolutions)
                else -> MovesSection(moves = moves)
            }
        }
    }
}

@Composable
private fun RoundedRectangleDecoration(modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .size(150.dp)
                .background(
                    color = Color(ROUNDED_RECTANGLE_COLOR),
                    shape = RoundedCornerShape(32.dp),
                )
    )
}

@Composable
private fun DottedDecoration(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.dotted),
        contentDescription = null,
        modifier = modifier.size(width = 63.dp, height = 34.dp),
        alpha = 0.3f,
    )
}

@Composable
private fun Header(modifier: Modifier = Modifier, pokemon: Pokemon) {
    Column(modifier.padding(top = 40.dp, bottom = 32.dp, start = 24.dp, end = 24.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = pokemon.name,
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.alignByBaseline(),
            )
            Text(
                text = formatId(pokemon.id),
                style = MaterialTheme.typography.displaySmall,
                modifier =
                    Modifier.alignByBaseline().graphicsLayer { alpha = Emphasis.Medium.alpha },
            )
        }
        Spacer(Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth()) {
            PokemonTypeLabels(types = pokemon.typeOfPokemon, metrics = MEDIUM)
        }
    }
}

@Composable
private fun RotatingPokeBall(
    modifier: Modifier = Modifier,
    tint: Color = Color(DEFAULT_POKEBALL_TINT),
) {
    val infiniteTransition = rememberInfiniteTransition(label = "rotatingPokeball")
    val angle by
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec =
                infiniteRepeatable(animation = tween(durationMillis = 4000, easing = LinearEasing)),
            label = "rotatingAngle",
        )

    Pokeball(tint = tint, modifier = modifier.graphicsLayer { rotationZ = angle })
}

@PreviewLightDark
@Composable
private fun PokemonDetailsPreview(
    @PreviewParameter(PokemonPreviewProvider::class) pokemon: Pokemon
) {
    var activePokemon by remember { mutableStateOf(pokemon) }

    AppTheme {
        Surface {
            AnimatedContent(targetState = activePokemon, label = "pokemonDetailsPreview") {
                targetPokemon ->
                PokemonTypesTheme(types = targetPokemon.typeOfPokemon) {
                    PokemonDetailsScreen(
                        pokemonSet = SamplePokemonData,
                        uiState = previewPokemonDetailsState(targetPokemon),
                        onPage = { activePokemon = it },
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
            AnimatedContent(targetState = true, label = "") { targetState ->
                if (targetState) {
                    PokemonTypeColorOverlay(
                        types = activePokemon.typeOfPokemon,
                        paletteStyle = paletteStyle,
                    ) {
                        PokemonDetailsScreen(
                            pokemonSet = SamplePokemonData,
                            uiState = previewPokemonDetailsState(activePokemon),
                        )
                    }
                }
            }
        }
    }
}

private fun previewPokemonDetailsState(pokemon: Pokemon) =
    PokemonDetailsUiState(
        details = pokemon,
        evolutions = mapSampleEvolutionsToList(pokemon.evolutionChain),
        moves = mapSampleMovesToDetailsList(),
        abilities = mapSampleAbilitiesToDetailsList(),
        isFavorite = false,
    )

class PokemonPreviewProvider : PreviewParameterProvider<Pokemon> {
    override val values =
        sequenceOf(
            SamplePokemonData[0],
            SamplePokemonData[CHARMANDER_SAMPLE_INDEX],
            SamplePokemonData[SQUIRTLE_SAMPLE_INDEX],
            SamplePokemonData.last(),
        )
}

class PaletteStyleProvider : PreviewParameterProvider<PaletteStyle> {
    override val values = AppPaletteStyles.asSequence()
}
