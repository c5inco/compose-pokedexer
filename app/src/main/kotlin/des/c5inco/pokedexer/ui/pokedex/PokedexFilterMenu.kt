package des.c5inco.pokedexer.ui.pokedex

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.R
import des.c5inco.pokedexer.shared.model.Generation
import des.c5inco.pokedexer.shared.model.Type

enum class FilterMenuState {
    Hidden,
    Visible,
    Types,
    Generations,
}

sealed class FilterMenuEvent {
    data class ToggleFavorites(val filterFavorites: Boolean) : FilterMenuEvent()

    data class ShowTypes(val showTypes: Boolean) : FilterMenuEvent()

    data class ShowGenerations(val showGenerations: Boolean) : FilterMenuEvent()

    data class FilterTypes(val typeToFilter: Type) : FilterMenuEvent()

    data class FilterGeneration(val generationToFilter: Generation) : FilterMenuEvent()
}

internal data class FilterMenuModel(
    val showFavorites: Boolean,
    val typeFilter: Type?,
    val generationFilter: Generation?,
    val menuState: FilterMenuState,
)

internal fun FilterMenuState.afterButtonClick(): FilterMenuState =
    when (this) {
        FilterMenuState.Hidden -> FilterMenuState.Visible
        FilterMenuState.Visible -> FilterMenuState.Hidden
        FilterMenuState.Types -> FilterMenuState.Visible
        FilterMenuState.Generations -> FilterMenuState.Visible
    }

internal fun FilterMenuState.after(event: FilterMenuEvent): FilterMenuState =
    when (event) {
        is FilterMenuEvent.ShowTypes -> FilterMenuState.Types
        is FilterMenuEvent.ShowGenerations -> FilterMenuState.Generations
        is FilterMenuEvent.ToggleFavorites,
        is FilterMenuEvent.FilterTypes,
        is FilterMenuEvent.FilterGeneration -> FilterMenuState.Hidden
    }

internal val FilterMenuEvent.opensSubmenu: Boolean
    get() = this is FilterMenuEvent.ShowTypes || this is FilterMenuEvent.ShowGenerations

@Composable
internal fun BoxScope.FilterScrim(visible: Boolean, onDismiss: () -> Unit) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = Modifier.matchParentSize(),
    ) {
        Box(
            Modifier.background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.5f))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onDismiss,
                )
        )
    }
}

@Composable
internal fun FilterControls(
    state: FilterMenuModel,
    onMenuItemClick: (FilterMenuEvent) -> Unit,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
            modifier.padding(WindowInsets.navigationBars.asPaddingValues()).padding(bottom = 24.dp),
    ) {
        if (state.menuState != FilterMenuState.Hidden) {
            FilterMenu(state = state, onMenuItemClick = onMenuItemClick)
        }
        Spacer(Modifier.height(16.dp))
        FloatingActionButton(
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.primary,
            onClick = onButtonClick,
        ) {
            AnimatedContent(targetState = state.menuState, label = "filterMenuButtonTransition") {
                targetState ->
                FilterMenuButtonIcon(targetState)
            }
        }
    }
}

@Composable
private fun FilterMenuButtonIcon(state: FilterMenuState) {
    when (state) {
        FilterMenuState.Hidden ->
            Icon(
                painter = painterResource(id = R.drawable.ic_filter),
                contentDescription = "Show filters",
            )
        FilterMenuState.Visible ->
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "Hide filters",
            )
        FilterMenuState.Types,
        FilterMenuState.Generations ->
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back to filter menu",
            )
    }
}

@Composable
private fun FilterMenu(state: FilterMenuModel, onMenuItemClick: (FilterMenuEvent) -> Unit) {
    AnimatedContent(
        targetState = state.menuState,
        transitionSpec = {
            EnterTransition.None togetherWith ExitTransition.None using SizeTransform(false)
        },
        label = "filterMenuTransition",
        modifier = Modifier.fillMaxWidth(),
    ) { targetState ->
        when (targetState) {
            FilterMenuState.Types -> TypeFilterMenu(state.typeFilter, onMenuItemClick)
            FilterMenuState.Generations ->
                GenerationFilterMenu(state.generationFilter, onMenuItemClick)
            FilterMenuState.Hidden -> Unit
            FilterMenuState.Visible -> MainFilterMenu(state, onMenuItemClick)
        }
    }
}

@Composable
private fun AnimatedVisibilityScope.TypeFilterMenu(
    selectedType: Type?,
    onMenuItemClick: (FilterMenuEvent) -> Unit,
) {
    FlowRow(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(horizontal = 24.dp),
    ) {
        Type.entries.forEachIndexed { index, type ->
            FilterTypeItem(
                type = type,
                selected = type == selectedType,
                index = index,
                onClick = { onMenuItemClick(FilterMenuEvent.FilterTypes(type)) },
                modifier = Modifier.padding(horizontal = 4.dp),
            )
        }
    }
}

@Composable
private fun AnimatedVisibilityScope.GenerationFilterMenu(
    selectedGeneration: Generation?,
    onMenuItemClick: (FilterMenuEvent) -> Unit,
) {
    FlowRow(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(horizontal = 24.dp),
    ) {
        Generation.entries.forEachIndexed { index, generation ->
            FilterGenerationItem(
                generation = generation,
                selected = generation == selectedGeneration,
                index = index,
                onClick = { onMenuItemClick(FilterMenuEvent.FilterGeneration(generation)) },
                modifier = Modifier.padding(horizontal = 4.dp),
            )
        }
    }
}

@Composable
private fun AnimatedVisibilityScope.MainFilterMenu(
    state: FilterMenuModel,
    onMenuItemClick: (FilterMenuEvent) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        FilterMenuItem(
            index = 0,
            onClick = { onMenuItemClick(FilterMenuEvent.ToggleFavorites(!state.showFavorites)) },
        ) {
            Icon(
                imageVector =
                    if (state.showFavorites) Icons.Default.FavoriteBorder
                    else Icons.Default.Favorite,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
            )
            Spacer(Modifier.width(8.dp))
            Text(if (state.showFavorites) "Show all" else "Show favorites")
        }
        FilterMenuItem(index = 1, onClick = { onMenuItemClick(FilterMenuEvent.ShowTypes(true)) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_genetics),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
            )
            Spacer(Modifier.width(8.dp))
            Text(if (state.typeFilter != null) "Filtered by ${state.typeFilter}" else "All types")
        }
        FilterMenuItem(
            index = 2,
            onClick = { onMenuItemClick(FilterMenuEvent.ShowGenerations(true)) },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_filter),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
            )
            Spacer(Modifier.width(8.dp))
            Text(
                if (state.generationFilter != null) "Gen ${state.generationFilter.romanNumeral}"
                else "All generations"
            )
        }
    }
}
