package des.c5inco.pokedexer.ui.pokedex

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import com.skydoves.landscapist.coil.CoilImage
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.data.pokemon.placeholderPokemonImage
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.ui.common.PokeBall
import des.c5inco.pokedexer.ui.common.PokemonTypeLabels
import des.c5inco.pokedexer.ui.common.TypeLabelMetrics
import des.c5inco.pokedexer.ui.common.artworkUrl
import des.c5inco.pokedexer.ui.common.formatId
import des.c5inco.pokedexer.ui.theme.AppTheme
import des.c5inco.pokedexer.ui.theme.PokemonTypesTheme

@Composable
fun PokeDexCard(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    isFavorite: Boolean = false,
    onPokemonSelected: (Pokemon) -> Unit = {}
) {
    PokemonTypesTheme(types = pokemon.typeOfPokemon) {
        Surface(
            modifier = modifier,
            shape = MaterialTheme.shapes.large,
            color = PokemonTypesTheme.colorScheme.surface
        ) {
            Box(modifier
                .height(120.dp)
                .clickable { onPokemonSelected(pokemon) }
            ) {
                Column(
                    Modifier.padding(top = 24.dp, start = 12.dp)
                ) {
                    PokemonName(pokemon.name)
                    Spacer(Modifier.height(8.dp))
                    PokemonTypeLabels(types = pokemon.typeOfPokemon, metrics = TypeLabelMetrics.SMALL)
                }
                Text(
                    text = formatId(pokemon.id),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 10.dp, end = 12.dp)
                        .graphicsLayer {
                            alpha = 0.5f
                        },
                )
                PokeBall(
                    Modifier
                        .align(Alignment.BottomEnd)
                        .offset(x = 5.dp, y = 10.dp)
                        .requiredSize(96.dp),
                    Color.White,
                    0.25f
                )

                CoilImage(
                    imageModel = { artworkUrl(pokemon.image) },
                    previewPlaceholder = placeholderPokemonImage(pokemon.image),
                    success = { imageState ->
                        imageState.drawable?.let {
                            Image(
                                bitmap = it.toBitmap().asImageBitmap(),
                                contentDescription = pokemon.name,
                            )
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 6.dp, end = 6.dp)
                        .size(80.dp)
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

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview
@Composable
private fun PokeDexCardPreview() {
    AppTheme {
        Surface {
            Column(
                Modifier.width(200.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                PokeDexCard(
                    modifier = Modifier.fillMaxWidth(),
                    pokemon = SamplePokemonData[0]
                )
                PokeDexCard(
                    modifier = Modifier.fillMaxWidth(),
                    pokemon = SamplePokemonData[3],
                    isFavorite = true
                )
                PokeDexCard(
                    modifier = Modifier.fillMaxWidth(),
                    pokemon = SamplePokemonData[6]
                )
            }
        }
    }
}