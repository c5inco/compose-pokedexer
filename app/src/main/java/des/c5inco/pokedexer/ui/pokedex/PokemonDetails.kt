package des.c5inco.pokedexer.ui.pokedex

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import des.c5inco.pokedexer.R
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.data.pokemon.mapSampleEvolutionsToList
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.model.color
import des.c5inco.pokedexer.ui.common.*
import des.c5inco.pokedexer.ui.common.TypeLabelMetrics.Companion.MEDIUM
import des.c5inco.pokedexer.ui.pokedex.section.AboutSection
import des.c5inco.pokedexer.ui.pokedex.section.BaseStatsSection
import des.c5inco.pokedexer.ui.pokedex.section.EvolutionSection
import des.c5inco.pokedexer.ui.pokedex.section.MovesSection
import des.c5inco.pokedexer.ui.theme.Theme.Companion.PokedexerTheme

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun PokemonDetailsRoute(
    viewModel: PokedexViewModel,
    detailsViewModel: PokemonDetailsViewModel,
    onBackClick: () -> Unit,
) {
    PokemonDetails(
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

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun PokemonDetails(
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
                    .padding(top = 140.dp)
            )
            Box(
                Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(top = 16.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        modifier = Modifier,
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back to Pokedex",
                            tint = Color.White
                        )
                    }
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
                }
                HeaderLeft(pokemon = pokemon)
                HeaderRight(
                    modifier = Modifier.align(Alignment.TopEnd),
                    pokemon = pokemon
                )
                CardContent(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 300.dp),
                    pokemon = pokemon,
                    evolutions = evolutions,
                )

                PokemonPager(
                    modifier = Modifier.padding(top = 116.dp),
                    loading = loading,
                    pokemonList = pokemonSet,
                    backgroundColor = pokemon.color(),
                    pagerState = pagerState,
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
                    Tab(
                        selected = index == section.ordinal,
                        onClick = { section = Sections.values()[index] },
                    ) {
                        Text(text, modifier = Modifier.padding(vertical = 20.dp))
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
            .size(200.dp)
            .graphicsLayer {
                rotationZ = angle
            },
        tint = Color(0xffF5F5F5),
        alpha = 0.25f
    )
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview
@Composable
private fun PokemonDetailsPreview() {
    var activePokemon by remember { mutableStateOf(SamplePokemonData.first()) }

    PokedexerTheme {
        Surface(Modifier.fillMaxSize()) {
            PokemonDetails(loading = false,
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