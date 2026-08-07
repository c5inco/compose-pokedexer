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
import des.c5inco.pokedexer.shared.model.Pokemon
import des.c5inco.pokedexer.shared.model.mapTypeToCuratedAnalogousHue
import des.c5inco.pokedexer.ui.common.Pokeball
import des.c5inco.pokedexer.ui.common.PokemonImage
import des.c5inco.pokedexer.ui.common.PokemonTypeLabels
import des.c5inco.pokedexer.ui.common.TypeLabelMetrics
import des.c5inco.pokedexer.ui.common.calculateAnalogousColors
import des.c5inco.pokedexer.ui.common.formatId
import des.c5inco.pokedexer.ui.common.meshGradient
import des.c5inco.pokedexer.ui.theme.AppTheme
import des.c5inco.pokedexer.ui.theme.PokemonTypesTheme

private const val ANALOGOUS_HUE_ANGLE = 18f
private const val MESH_RESOLUTION = 10
private const val DARK_THEME_ID_ALPHA = 0.5f
private const val LIGHT_THEME_ID_ALPHA = 0.7f
private const val POKEBALL_ALPHA = 0.25f

private object PokedexCardMeshCoordinates {
    const val TOP_SECOND_X = 0.24099097f
    const val TOP_THIRD_X = 0.5358101f
    const val TOP_FOURTH_X = 0.7894143f
    const val MIDDLE_START_Y = 0.5f
    const val MIDDLE_SECOND_X = 0.24236615f
    const val MIDDLE_SECOND_Y = 0.6261937f
    const val MIDDLE_THIRD_X = 0.5254497f
    const val MIDDLE_THIRD_Y = 0.4176749f
    const val MIDDLE_FOURTH_X = 0.802476f
    const val MIDDLE_FOURTH_Y = 0.6690188f
    const val MIDDLE_END_Y = 0.35517487f
    const val BOTTOM_SECOND_X = 0.23941448f
    const val BOTTOM_THIRD_X = 0.5159903f
    const val BOTTOM_FOURTH_X = 0.7876128f
}

private fun buildPokedexCardMeshColors(
    analogousSurfaceColor: Color,
    pokemonTypeSurfaceColor: Color,
): List<List<Pair<Offset, Color>>> =
    listOf(
        listOf(
            Offset(0.0f, 0.0f) to analogousSurfaceColor,
            Offset(PokedexCardMeshCoordinates.TOP_SECOND_X, 0.0f) to analogousSurfaceColor,
            Offset(PokedexCardMeshCoordinates.TOP_THIRD_X, 0.0f) to analogousSurfaceColor,
            Offset(PokedexCardMeshCoordinates.TOP_FOURTH_X, 0.0f) to analogousSurfaceColor,
            Offset(1.0f, 0.0f) to pokemonTypeSurfaceColor,
        ),
        listOf(
            Offset(0.0f, PokedexCardMeshCoordinates.MIDDLE_START_Y) to analogousSurfaceColor,
            Offset(
                PokedexCardMeshCoordinates.MIDDLE_SECOND_X,
                PokedexCardMeshCoordinates.MIDDLE_SECOND_Y,
            ) to pokemonTypeSurfaceColor,
            Offset(
                PokedexCardMeshCoordinates.MIDDLE_THIRD_X,
                PokedexCardMeshCoordinates.MIDDLE_THIRD_Y,
            ) to pokemonTypeSurfaceColor,
            Offset(
                PokedexCardMeshCoordinates.MIDDLE_FOURTH_X,
                PokedexCardMeshCoordinates.MIDDLE_FOURTH_Y,
            ) to analogousSurfaceColor,
            Offset(1.0f, PokedexCardMeshCoordinates.MIDDLE_END_Y) to pokemonTypeSurfaceColor,
        ),
        listOf(
            Offset(0.0f, 1.0f) to pokemonTypeSurfaceColor,
            Offset(PokedexCardMeshCoordinates.BOTTOM_SECOND_X, 1.0f) to pokemonTypeSurfaceColor,
            Offset(PokedexCardMeshCoordinates.BOTTOM_THIRD_X, 1.0f) to pokemonTypeSurfaceColor,
            Offset(PokedexCardMeshCoordinates.BOTTOM_FOURTH_X, 1.0f) to pokemonTypeSurfaceColor,
            Offset(1.0f, 1.0f) to pokemonTypeSurfaceColor,
        ),
    )

@Composable
fun PokedexCard(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    isFavorite: Boolean = false,
    onPokemonSelected: (Pokemon) -> Unit = {},
) {
    PokemonTypesTheme(types = pokemon.typeOfPokemon) {
        val pokemonTypeSurfaceColor = PokemonTypesTheme.colorScheme.surface
        val hueIndex = mapTypeToCuratedAnalogousHue(PokemonTypesTheme.colorScheme.type)
        val analogousSurfaceColor =
            remember(pokemonTypeSurfaceColor) {
                calculateAnalogousColors(pokemonTypeSurfaceColor, ANALOGOUS_HUE_ANGLE)[hueIndex]
            }

        val colors =
            remember(analogousSurfaceColor, pokemonTypeSurfaceColor) {
                buildPokedexCardMeshColors(
                    analogousSurfaceColor = analogousSurfaceColor,
                    pokemonTypeSurfaceColor = pokemonTypeSurfaceColor,
                )
            }

        Surface(
            modifier =
                modifier
                    .clip(MaterialTheme.shapes.large)
                    .meshGradient(
                        points = colors,
                        resolutionX = MESH_RESOLUTION,
                        resolutionY = MESH_RESOLUTION,
                    ),
            shape = MaterialTheme.shapes.large,
            color = Color.Transparent,
            contentColor = PokemonTypesTheme.colorScheme.onSurface,
        ) {
            Box(modifier.height(124.dp).clickable { onPokemonSelected(pokemon) }) {
                Column(Modifier.padding(top = 24.dp, start = 12.dp)) {
                    PokemonName(pokemon.name)
                    Spacer(Modifier.height(8.dp))
                    PokemonTypeLabels(
                        types = pokemon.typeOfPokemon,
                        metrics = TypeLabelMetrics.SMALL,
                    )
                }
                val idAlpha =
                    if (isSystemInDarkTheme()) DARK_THEME_ID_ALPHA else LIGHT_THEME_ID_ALPHA
                Text(
                    text = formatId(pokemon.id),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier =
                        Modifier.graphicsLayer { alpha = idAlpha }
                            .padding(top = 8.dp, end = 12.dp)
                            .align(Alignment.TopEnd),
                )
                Pokeball(
                    tint = Color.White,
                    modifier =
                        Modifier.requiredSize(88.dp)
                            .graphicsLayer { alpha = POKEBALL_ALPHA }
                            .align(Alignment.BottomEnd)
                            .offset(x = 0.dp, y = 0.dp),
                )

                PokemonImage(
                    image = pokemon.image,
                    description = pokemon.name,
                    modifier =
                        Modifier.padding(bottom = 6.dp, end = 6.dp)
                            .size(80.dp)
                            .align(Alignment.BottomEnd),
                )
                if (isFavorite) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        modifier =
                            Modifier.align(Alignment.BottomEnd).padding(bottom = 8.dp, end = 8.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun PokemonName(name: String?) {
    Text(text = name ?: "", fontWeight = FontWeight.Bold, fontSize = 14.sp)
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
                contentPadding = PaddingValues(12.dp),
            ) {
                items(ids) { PokedexCard(pokemon = SamplePokemonData[it]) }
            }
        }
    }
}
