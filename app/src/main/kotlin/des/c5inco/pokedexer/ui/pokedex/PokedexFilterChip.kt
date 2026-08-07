package des.c5inco.pokedexer.ui.pokedex

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.materialkolor.PaletteStyle
import des.c5inco.pokedexer.shared.model.Generation
import des.c5inco.pokedexer.shared.model.Type
import des.c5inco.pokedexer.ui.common.mapTypeToIcon
import des.c5inco.pokedexer.ui.theme.getDynamicColorScheme
import des.c5inco.pokedexer.ui.theme.mapDynamicPokemonColorScheme
import des.c5inco.pokedexer.ui.theme.mapTypeToSeedColor

private const val UNSELECTED_TYPE_ICON_ALPHA = 0.4f

private data class FilterChipStyle(
    val index: Int,
    val colors: ButtonColors,
    val selected: Boolean,
    val contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
)

@Composable
internal fun AnimatedVisibilityScope.FilterMenuItem(
    modifier: Modifier = Modifier,
    index: Int,
    onClick: () -> Unit = {},
    content: @Composable RowScope.() -> Unit = {},
) {
    FilledTonalButton(
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 8.dp),
        onClick = onClick,
        modifier =
            modifier.animateEnterExit(
                enter =
                    fadeIn(
                        animationSpec = tween(durationMillis = 200, delayMillis = index * 15 + 60)
                    ) +
                        slideInVertically(
                            initialOffsetY = { it / 2 },
                            animationSpec =
                                tween(durationMillis = 240, delayMillis = index * 50 + 60),
                        ),
                exit =
                    fadeOut(animationSpec = spring(stiffness = Spring.StiffnessMedium)) +
                        slideOutVertically(targetOffsetY = { it / 2 }),
                label = "filterMenuItemTransition",
            ),
    ) {
        content()
    }
}

@Composable
private fun AnimatedVisibilityScope.FilterChip(
    modifier: Modifier = Modifier,
    style: FilterChipStyle,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val cornerRadius by
        animateDpAsState(
            targetValue =
                when {
                    isPressed -> 8.dp
                    style.selected -> 12.dp
                    else -> 24.dp
                },
            animationSpec = spring(dampingRatio = 0.9f, stiffness = 1400f),
            label = "cornerRadius",
        )

    FilledTonalButton(
        contentPadding = style.contentPadding,
        onClick = onClick,
        colors = style.colors,
        shape = RoundedCornerShape(cornerRadius),
        interactionSource = interactionSource,
        modifier =
            modifier.animateEnterExit(
                enter =
                    fadeIn(
                        animationSpec =
                            tween(durationMillis = 240, delayMillis = style.index * 15 + 60)
                    ) +
                        slideInVertically(
                            initialOffsetY = { it / 2 },
                            animationSpec =
                                tween(durationMillis = 150, delayMillis = style.index * 15 + 60),
                        ),
                exit = fadeOut(animationSpec = spring(stiffness = Spring.StiffnessMedium)),
                label = "filterChipTransition",
            ),
    ) {
        content()
    }
}

@Composable
internal fun AnimatedVisibilityScope.FilterTypeItem(
    modifier: Modifier = Modifier,
    type: Type,
    selected: Boolean = false,
    index: Int,
    onClick: () -> Unit = {},
) {
    val seedColor = mapTypeToSeedColor(types = listOf(type.toString()))
    val kolorScheme = getDynamicColorScheme(seedColor, PaletteStyle.Rainbow)
    val pokemonColorScheme =
        mapDynamicPokemonColorScheme(seedColor = seedColor, colorScheme = kolorScheme)
    val colors =
        if (selected) {
            ButtonDefaults.filledTonalButtonColors(
                containerColor = pokemonColorScheme.surface,
                contentColor = pokemonColorScheme.onSurface,
            )
        } else {
            ButtonDefaults.filledTonalButtonColors()
        }

    FilterChip(
        modifier = modifier,
        style =
            FilterChipStyle(
                index = index,
                colors = colors,
                selected = selected,
                contentPadding =
                    PaddingValues(start = 12.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
            ),
        onClick = onClick,
    ) {
        Icon(
            painter = painterResource(id = mapTypeToIcon(type)),
            contentDescription = null,
            modifier =
                Modifier.size(18.dp).graphicsLayer {
                    alpha = if (selected) 1f else UNSELECTED_TYPE_ICON_ALPHA
                },
        )
        Spacer(Modifier.width(4.dp))
        Text("$type")
    }
}

@Composable
internal fun AnimatedVisibilityScope.FilterGenerationItem(
    modifier: Modifier = Modifier,
    generation: Generation,
    selected: Boolean = false,
    index: Int,
    onClick: () -> Unit = {},
) {
    val colors =
        if (selected) {
            ButtonDefaults.filledTonalButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            )
        } else {
            ButtonDefaults.filledTonalButtonColors()
        }

    FilterChip(
        modifier = modifier,
        style =
            FilterChipStyle(
                index = index,
                colors = colors,
                selected = selected,
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
            ),
        onClick = onClick,
    ) {
        Text(text = "Gen ${generation.romanNumeral}")
    }
}
