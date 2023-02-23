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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import des.c5inco.pokedexer.model.mapTypesToOnSurfaceColor
import des.c5inco.pokedexer.model.mapTypesToPrimaryColor
import des.c5inco.pokedexer.model.mapTypesToSurfaceColor
import des.c5inco.pokedexer.ui.common.PokeBall
import des.c5inco.pokedexer.ui.common.PokemonTypeLabels
import des.c5inco.pokedexer.ui.common.TypeLabelMetrics
import des.c5inco.pokedexer.ui.common.artworkUrl
import des.c5inco.pokedexer.ui.common.formatId
import des.c5inco.pokedexer.ui.theme.M3Theme
import androidx.compose.material3.LocalContentColor as M3LocalContentColor
import androidx.compose.material3.Surface as M3Surface
import androidx.compose.material3.Text as M3Text

@Composable
fun PokeDexCard(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    onPokemonSelected: (Pokemon) -> Unit = {}
) {
    M3Surface(
        modifier = modifier,
        color = mapTypesToSurfaceColor(types = pokemon.typeOfPokemon),
        shape = RoundedCornerShape(16.dp)
    ) {
        PokeDexCardContent(
            modifier = Modifier.clickable {
                onPokemonSelected(pokemon)
            },
            pokemon = pokemon
        )
    }
}

@Composable
private fun PokeDexCardContent(
    modifier: Modifier = Modifier,
    pokemon: Pokemon
) {
    val typeContentColor = mapTypesToOnSurfaceColor(types = pokemon.typeOfPokemon)

    CompositionLocalProvider(M3LocalContentColor provides typeContentColor) {
        Box(modifier.height(120.dp)) {
            Column(
                Modifier.padding(top = 24.dp, start = 12.dp)
            ) {
                PokemonName(pokemon.name)
                Spacer(Modifier.height(8.dp))
                PokemonTypeLabels(pokemon.typeOfPokemon, TypeLabelMetrics.SMALL)
            }
            M3Text(
                text = formatId(pokemon.id),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = mapTypesToPrimaryColor(types = pokemon.typeOfPokemon),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 10.dp, end = 12.dp)
                    .graphicsLayer {
                        alpha = 1f
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
        }
    }
}

@Composable
private fun PokemonName(name: String?) {
    M3Text(
        text = name ?: "",
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
    )
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview
@Composable
private fun PokeDexCardPreview() {
    M3Theme {
        M3Surface {
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
                    pokemon = SamplePokemonData[3]
                )
                PokeDexCard(
                    modifier = Modifier.fillMaxWidth(),
                    pokemon = SamplePokemonData[6]
                )
            }
        }
    }
}