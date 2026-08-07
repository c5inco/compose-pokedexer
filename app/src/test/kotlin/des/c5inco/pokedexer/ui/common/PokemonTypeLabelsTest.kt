package des.c5inco.pokedexer.ui.common

import des.c5inco.pokedexer.R
import des.c5inco.pokedexer.shared.model.Type
import org.junit.Assert.assertEquals
import org.junit.Test

class PokemonTypeLabelsTest {
    @Test
    fun mapTypeToIconReturnsExpectedIconForEveryType() {
        val expectedIcons =
            mapOf(
                Type.Normal to R.drawable.ic_type_normal,
                Type.Fire to R.drawable.ic_type_fire,
                Type.Water to R.drawable.ic_type_water,
                Type.Electric to R.drawable.ic_type_electric,
                Type.Grass to R.drawable.ic_type_grass,
                Type.Ice to R.drawable.ic_type_ice,
                Type.Fighting to R.drawable.ic_type_fighting,
                Type.Poison to R.drawable.ic_type_poison,
                Type.Ground to R.drawable.ic_type_ground,
                Type.Flying to R.drawable.ic_type_flying,
                Type.Psychic to R.drawable.ic_type_psychic,
                Type.Bug to R.drawable.ic_type_bug,
                Type.Rock to R.drawable.ic_type_rock,
                Type.Ghost to R.drawable.ic_type_ghost,
                Type.Dragon to R.drawable.ic_type_dragon,
                Type.Dark to R.drawable.ic_type_dark,
                Type.Steel to R.drawable.ic_type_steel,
                Type.Fairy to R.drawable.ic_type_fairy,
            )

        assertEquals(Type.entries.toSet(), expectedIcons.keys)
        expectedIcons.forEach { (type, icon) -> assertEquals(type.name, icon, mapTypeToIcon(type)) }
    }
}
