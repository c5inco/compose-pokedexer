package des.c5inco.pokedexer.ui.pokedex

import des.c5inco.pokedexer.shared.model.Generation
import des.c5inco.pokedexer.shared.model.Type
import org.junit.Assert.assertEquals
import org.junit.Test

class FilterMenuStateTest {
    @Test
    fun filterButtonCyclesBetweenHiddenVisibleAndParentMenu() {
        assertEquals(FilterMenuState.Visible, FilterMenuState.Hidden.afterButtonClick())
        assertEquals(FilterMenuState.Hidden, FilterMenuState.Visible.afterButtonClick())
        assertEquals(FilterMenuState.Visible, FilterMenuState.Types.afterButtonClick())
        assertEquals(FilterMenuState.Visible, FilterMenuState.Generations.afterButtonClick())
    }

    @Test
    fun submenuEventsOpenSubmenusAndAppliedFiltersHideMenu() {
        assertEquals(
            FilterMenuState.Types,
            FilterMenuState.Visible.after(FilterMenuEvent.ShowTypes(showTypes = true)),
        )
        assertEquals(
            FilterMenuState.Generations,
            FilterMenuState.Visible.after(FilterMenuEvent.ShowGenerations(showGenerations = true)),
        )
        assertEquals(
            FilterMenuState.Hidden,
            FilterMenuState.Types.after(FilterMenuEvent.FilterTypes(Type.Fire)),
        )
        assertEquals(
            FilterMenuState.Hidden,
            FilterMenuState.Generations.after(FilterMenuEvent.FilterGeneration(Generation.I)),
        )
        assertEquals(
            FilterMenuState.Hidden,
            FilterMenuState.Visible.after(FilterMenuEvent.ToggleFavorites(filterFavorites = true)),
        )
    }
}
