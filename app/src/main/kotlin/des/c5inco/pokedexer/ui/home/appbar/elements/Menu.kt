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
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.R
import des.c5inco.pokedexer.model.Type
import des.c5inco.pokedexer.ui.theme.AppTheme
import des.c5inco.pokedexer.ui.theme.PokemonTypesTheme

sealed class MenuItem(
    val label: String,
    val typeColor: Type
) {
    object Pokedex : MenuItem("Pokedex", Type.Grass)
    object Moves : MenuItem("Moves", Type.Fire)
    object Abilities : MenuItem("Abilities", Type.Water)
    object Items : MenuItem("Items", Type.Electric)
    object Locations : MenuItem("Locations", Type.Dragon)
    object TypeCharts : MenuItem("Type charts", Type.Water)
}

@Composable
fun Menu(
    modifier: Modifier = Modifier,
    onMenuItemSelected: (MenuItem) -> Unit = {}
) {
    val menuItems = listOf(MenuItem.Pokedex, MenuItem.Moves, MenuItem.TypeCharts, MenuItem.Items)

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        for (i in menuItems.indices step 2) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PokemonTypesTheme(types = listOf(menuItems[i].typeColor.name)) {
                    MenuItemButton(
                        modifier = Modifier.weight(1f),
                        item = menuItems[i]
                    ) {
                        onMenuItemSelected(menuItems[i])
                    }
                }
                PokemonTypesTheme(types = listOf(menuItems[i + 1].typeColor.name)) {
                    MenuItemButton(
                        modifier = Modifier.weight(1f),
                        item = menuItems[i + 1]
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
    item: MenuItem,
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
                    .height(128.dp)
                    .clickable { onClick() },
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 16.dp, bottom = 16.dp),
                    text = item.label,
                    color = Color.White
                )
                Icon(
                    painter = painterResource(id = mapMenuItemToIcon(item)),
                    contentDescription = "Pokedex",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = (-8).dp, y = 16.dp)
                        .requiredSize(72.dp),
                    tint = Color.White.copy(alpha = 0.3f)
                )
            }
        }
    }
}

fun mapMenuItemToIcon(
    item: MenuItem
): Int {
    return when (item) {
        MenuItem.Pokedex -> R.drawable.ic_catching_pokemon
        MenuItem.Moves -> R.drawable.ic_fitness_center
        MenuItem.Abilities -> R.drawable.ic_stream
        MenuItem.Items -> R.drawable.ic_store_mall_directory
        MenuItem.TypeCharts -> R.drawable.ic_genetics
        else -> R.drawable.ic_catching_pokemon
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