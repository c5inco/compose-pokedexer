package des.c5inco.pokedexer.ui.pokedex

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.model.mapTypeToCuratedAnalogousHue
import des.c5inco.pokedexer.ui.common.Pokeball
import des.c5inco.pokedexer.ui.common.PokemonImage
import des.c5inco.pokedexer.ui.common.PokemonTypeLabels
import des.c5inco.pokedexer.ui.common.TypeLabelMetrics
import des.c5inco.pokedexer.ui.common.calculateAnalogousColors
import des.c5inco.pokedexer.ui.common.formatId
import des.c5inco.pokedexer.ui.common.meshGradient
import des.c5inco.pokedexer.ui.theme.AppTheme
import des.c5inco.pokedexer.ui.theme.PokemonTypesTheme

@Composable
fun PokedexCard(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    isFavorite: Boolean = false,
    onPokemonSelected: (Pokemon) -> Unit = {}
) {
    PokemonTypesTheme(types = pokemon.typeOfPokemon) {
        val pokemonTypeSurfaceColor = PokemonTypesTheme.colorScheme.surface
        val hueIndex = mapTypeToCuratedAnalogousHue(PokemonTypesTheme.colorScheme.type)
        val analogousSurfaceColor = remember(pokemonTypeSurfaceColor) {
            calculateAnalogousColors(pokemonTypeSurfaceColor, 18f)[hueIndex]
        }

        val colors = listOf(
            listOf(
                Offset(0.0f, 0.0f) to analogousSurfaceColor,
                Offset(0.24099097f, 0.0f) to analogousSurfaceColor,
                Offset(0.5358101f, 0.0f) to analogousSurfaceColor,
                Offset(0.7894143f, 0.0f) to analogousSurfaceColor,
                Offset(1.0f, 0.0f) to pokemonTypeSurfaceColor,
            ),
            listOf(
                Offset(0.0f, 0.5f) to analogousSurfaceColor,
                Offset(0.24236615f, 0.6261937f) to pokemonTypeSurfaceColor,
                Offset(0.5254497f, 0.4176749f) to pokemonTypeSurfaceColor,
                Offset(0.802476f, 0.6690188f) to analogousSurfaceColor,
                Offset(1.0f, 0.35517487f) to pokemonTypeSurfaceColor,
            ),
            listOf(
                Offset(0.0f, 1.0f) to pokemonTypeSurfaceColor,
                Offset(0.23941448f, 1.0f) to pokemonTypeSurfaceColor,
                Offset(0.5159903f, 1.0f) to pokemonTypeSurfaceColor,
                Offset(0.7876128f, 1.0f) to pokemonTypeSurfaceColor,
                Offset(1.0f, 1.0f) to pokemonTypeSurfaceColor,
            ),
        )

        // val colors = listOf(
        //     listOf(
        //         Offset(0f, 0f) to analogousSurfaceColor,
        //         Offset(.33f, 0f) to analogousSurfaceColor,
        //         Offset(.66f, 0f) to analogousSurfaceColor,
        //         Offset(1f, 0f) to analogousSurfaceColor,
        //     ),
        //     listOf(
        //         Offset(0f, .6f) to pokemonTypeSurfaceColor,
        //         Offset(.25f, .4f) to pokemonTypeSurfaceColor,
        //         Offset(.8f, .6f) to pokemonTypeSurfaceColor,
        //         Offset(1f, .5f) to pokemonTypeSurfaceColor,
        //     ),
        //     listOf(
        //         Offset(0f, 1f) to PokemonTypesTheme.colorScheme.primary,
        //         Offset(.33f, 1f) to PokemonTypesTheme.colorScheme.primary,
        //         Offset(.67f, 1f) to PokemonTypesTheme.colorScheme.primary,
        //         Offset(1f, 1f) to PokemonTypesTheme.colorScheme.primary,
        //     ),
        // )

        Surface(
            modifier = modifier
                .clip(MaterialTheme.shapes.large)
                .meshGradient(
                    points = colors,
                    resolutionX = 10,
                    resolutionY = 10,
                ),
            shape = MaterialTheme.shapes.large,
            color = Color.Transparent,
            contentColor = PokemonTypesTheme.colorScheme.onSurface
        ) {
            Box(
                modifier
                    .height(124.dp)
                    .clickable { onPokemonSelected(pokemon) }
            ) {
                Column(
                    Modifier.padding(top = 24.dp, start = 12.dp)
                ) {
                    PokemonName(pokemon.name)
                    Spacer(Modifier.height(8.dp))
                    PokemonTypeLabels(types = pokemon.typeOfPokemon, metrics = TypeLabelMetrics.SMALL)
                }
                val idAlpha = if (isSystemInDarkTheme()) 0.5f else 0.7f
                Text(
                    text = formatId(pokemon.id),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .graphicsLayer { alpha = idAlpha }
                        .padding(top = 8.dp, end = 12.dp)
                        .align(Alignment.TopEnd)
                )
                Pokeball(
                    tint = Color.White,
                    modifier = Modifier
                        .requiredSize(88.dp)
                        .graphicsLayer { alpha = 0.25f }
                        .align(Alignment.BottomEnd)
                        .offset(x = 0.dp, y = 0.dp)
                )

                PokemonImage(
                    image = pokemon.image,
                    description = pokemon.name,
                    modifier = Modifier
                        .padding(bottom = 6.dp, end = 6.dp)
                        .size(80.dp)
                        .align(Alignment.BottomEnd)
                )
                if (isFavorite) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(bottom = 8.dp, end = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun PokemonName(name: String?) {
    Text(
        text = name ?: "",
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
    )
}

@PreviewLightDark
@Composable
private fun PokedexCardPreview() {
    AppTheme {
        Surface {
            val ids = listOf(0, 3, 6) + (9..22).toList()
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(12.dp)
            ) {
                items(ids) {
                    PokedexCard(
                        pokemon = SamplePokemonData[it],
                    )
                }
            }
        }
    }
}