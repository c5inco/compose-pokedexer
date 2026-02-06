package des.c5inco.pokedexer.ui.home.appbar.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.shared.model.Pokemon
import des.c5inco.pokedexer.ui.common.Pokeball
import des.c5inco.pokedexer.ui.common.PokemonImage
import des.c5inco.pokedexer.ui.common.formatId
import des.c5inco.pokedexer.ui.theme.AppTheme
import des.c5inco.pokedexer.ui.theme.PokemonTypesTheme

@Composable
fun PokemonResultCard(
    modifier: Modifier = Modifier,
    pokemon: Pokemon = SamplePokemonData.first(),
    onPokemonSelected: (Pokemon) -> Unit = {}
) {
    PokemonTypesTheme(types = pokemon.typeOfPokemon) {
        Surface(
            modifier = modifier.width(200.dp),
            shape = MaterialTheme.shapes.large,
            color = PokemonTypesTheme.colorScheme.surface,
            contentColor = PokemonTypesTheme.colorScheme.onSurface
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .clickable { onPokemonSelected(pokemon) }
            ) {
                Column(
                    Modifier
                        .padding(start = 12.dp)
                        .align(Alignment.CenterStart)
                ) {
                    Text(
                        text = pokemon.name,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = formatId(pokemon.id),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .graphicsLayer {
                                alpha = 0.5f
                            },
                    )
                }
                Pokeball(
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(x = 16.dp, y = 12.dp)
                        .requiredSize(120.dp)
                        .graphicsLayer { alpha = 0.25f },
                )
                PokemonImage(
                    image = pokemon.image,
                    description = pokemon.name,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 2.dp, end = 6.dp)
                        .fillMaxHeight(.85f)
                )
            }
        }
    }
}

@Preview()
@Composable
fun PokemonResultCardPreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp)
        ) {
            LazyHorizontalGrid(
                rows = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            ) {
                items(SamplePokemonData.take(5)) {
                    PokemonResultCard(
                        pokemon = it
                    )
                }
            }
        }
    }
}

