package des.c5inco.pokedexer.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.style.*
import androidx.compose.foundation.style.MutableStyleState
import androidx.compose.foundation.style.Style
import androidx.compose.foundation.style.StyleScope
import androidx.compose.foundation.style.StyleStateKey
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val typeLabelColoredKey = StyleStateKey(false)

var MutableStyleState.isColored: Boolean
    get() = this[typeLabelColoredKey]
    set(value) {
        this[typeLabelColoredKey] = value
    }

private fun StyleScope.colored(value: Style) {
    state(typeLabelColoredKey, value) { key, state -> state[key] == true }
}

private fun StyleScope.uncolored(value: Style) {
    state(typeLabelColoredKey, value) { key, state -> state[key] == false }
}

object AppComponentStyles {
    val menuItemButtonStyle: Style
        @Composable
        @ReadOnlyComposable
        get() {
            val surfaceColor = LocalPokemonTypeColorScheme.current.surface
            return Style {
                shape(RoundedCornerShape(16.dp))
                background(surfaceColor)
                height(128.dp)
                contentColor(Color.White)
            }
        }

    val smallTypeLabelStyle: Style
        @Composable
        @ReadOnlyComposable
        get() =
            baseTypeLabelStyle then
                Style {
                    shape(RoundedCornerShape(24.dp))
                    fontSize(10.sp)
                    fontWeight(FontWeight.Normal)
                    contentPadding(horizontal = 10.dp, vertical = 0.dp)
                }

    val mediumTypeLabelStyle: Style
        @Composable
        @ReadOnlyComposable
        get() =
            baseTypeLabelStyle then
                Style {
                    shape(RoundedCornerShape(24.dp))
                    fontSize(12.sp)
                    fontWeight(FontWeight.Bold)
                    contentPadding(horizontal = 12.dp, vertical = 0.dp)
                }

    val largeTypeLabelStyle: Style
        @Composable
        @ReadOnlyComposable
        get() =
            baseTypeLabelStyle then
                Style {
                    shape(RoundedCornerShape(24.dp))
                    fontSize(14.sp)
                    fontWeight(FontWeight.Normal)
                    contentPadding(horizontal = 16.dp, vertical = 4.dp)
                }

    private val baseTypeLabelStyle: Style
        @Composable
        @ReadOnlyComposable
        get() {
            val surfaceColor = LocalPokemonTypeColorScheme.current.surface
            val onSurfaceColor = LocalPokemonTypeColorScheme.current.onSurface
            return Style {
                contentColor(onSurfaceColor)
                uncolored { background(Color(0x38FFFFFF)) }
                colored { background(surfaceColor) }
            }
        }

    val filterChipStyle: Style
        @Composable
        @ReadOnlyComposable
        get() {
            val colorScheme = MaterialTheme.colorScheme
            val typography = MaterialTheme.typography
            val pokemonColorScheme = LocalPokemonTypeColorScheme.current

            return Style {
                shape(RoundedCornerShape(24.dp))
                contentPadding(horizontal = 12.dp, vertical = 8.dp)
                background(colorScheme.secondaryContainer)
                contentColor(colorScheme.onSecondaryContainer)
                minHeight(40.dp)
                minWidth(58.dp)

                selected {
                    shape(RoundedCornerShape(12.dp))
                    background(pokemonColorScheme.surface)
                    contentColor(pokemonColorScheme.onSurface)
                }

                pressed {
                    shape(RoundedCornerShape(8.dp))
                }
            }
        }
}
