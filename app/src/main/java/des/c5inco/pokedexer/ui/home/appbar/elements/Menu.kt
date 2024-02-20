package des.c5inco.pokedexer.ui.home.appbar.elements

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.ui.common.PokeBall
import des.c5inco.pokedexer.ui.home.MenuItem
import des.c5inco.pokedexer.ui.home.MenuItem.Abilities
import des.c5inco.pokedexer.ui.home.MenuItem.Items
import des.c5inco.pokedexer.ui.home.MenuItem.Locations
import des.c5inco.pokedexer.ui.home.MenuItem.Moves
import des.c5inco.pokedexer.ui.home.MenuItem.Pokedex
import des.c5inco.pokedexer.ui.home.MenuItem.TypeCharts
import des.c5inco.pokedexer.ui.theme.AppTheme
import des.c5inco.pokedexer.ui.theme.PokemonTypesTheme

@Composable
fun Menu(
    modifier: Modifier = Modifier,
    onMenuItemSelected: (MenuItem) -> Unit = {}
) {
    val menuItems = listOf(
        Pokedex, Moves,
        Abilities, Items,
        Locations, TypeCharts
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        for (i in menuItems.indices step 2) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PokemonTypesTheme(types = listOf(menuItems[i].typeColor.name)) {
                    MenuItemButton(
                        modifier = Modifier.weight(1f),
                        text = menuItems[i].label
                    ) {
                        onMenuItemSelected(menuItems[i])
                    }
                }
                PokemonTypesTheme(types = listOf(menuItems[i + 1].typeColor.name)) {
                    MenuItemButton(
                        modifier = Modifier.weight(1f),
                        text = menuItems[i + 1].label
                    ) {
                        onMenuItemSelected(menuItems[i + 1])
                    }
                }
            }
        }
    }
}

@Composable
fun MenuItemButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Box(
                modifier = Modifier
                    .offset(y = 3.dp)
                    .customShadow(PokemonTypesTheme.colorScheme.surface)
            )
        }
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = PokemonTypesTheme.colorScheme.surface
        ) {
            Box(
                modifier = Modifier
                    .height(64.dp)
                    .clickable { onClick() },
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = text,
                    color = Color.White
                )
                PokeBall(
                    Modifier
                        .align(Alignment.TopStart)
                        .offset(x = (-30).dp, y = (-40).dp)
                        .requiredSize(60.dp),
                    Color.White,
                    0.15f
                )
                PokeBall(
                    Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 20.dp)
                        .requiredSize(96.dp),
                    Color.White,
                    0.15f
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
private fun Modifier.customShadow(
    color: Color,
): Modifier {
    return this.then(
        Modifier
            .height(12.dp)
            .fillMaxWidth(0.8f)
            .graphicsLayer {
                renderEffect = RenderEffect.createBlurEffect(
                    40f,40f, Shader.TileMode.DECAL
                ).asComposeRenderEffect()
            }
            .background(color, RoundedCornerShape(100))
    )
}

@Preview
@Composable
fun MenuPreview() {
    AppTheme {
        Surface {
            Menu()
        }
    }
}