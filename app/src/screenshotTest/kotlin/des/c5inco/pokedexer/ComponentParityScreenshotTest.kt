package des.c5inco.pokedexer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.tools.screenshot.PreviewTest
import des.c5inco.pokedexer.shared.model.Generation
import des.c5inco.pokedexer.shared.model.Type
import des.c5inco.pokedexer.ui.common.TypeLabel
import des.c5inco.pokedexer.ui.common.TypeLabelMetrics
import des.c5inco.pokedexer.ui.home.appbar.elements.MenuItem
import des.c5inco.pokedexer.ui.home.appbar.elements.MenuItemButton
import des.c5inco.pokedexer.ui.pokedex.FilterGenerationItemScreenshotPreview
import des.c5inco.pokedexer.ui.pokedex.FilterTypeItemScreenshotPreview
import des.c5inco.pokedexer.ui.theme.AppTheme
import des.c5inco.pokedexer.ui.theme.PokemonTypesTheme

class ComponentParityScreenshotTest {

    @Preview(name = "MenuItemButton Baseline", showBackground = true)
    @PreviewTest
    @Composable
    fun menuItemButtonBaseline() {
        AppTheme {
            Surface {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    PokemonTypesTheme(types = listOf(MenuItem.Pokedex.typeColor.name)) {
                        MenuItemButton(item = MenuItem.Pokedex, onClick = {})
                    }
                    PokemonTypesTheme(types = listOf(MenuItem.Moves.typeColor.name)) {
                        MenuItemButton(item = MenuItem.Moves, onClick = {})
                    }
                    PokemonTypesTheme(types = listOf(MenuItem.Items.typeColor.name)) {
                        MenuItemButton(item = MenuItem.Items, onClick = {})
                    }
                }
            }
        }
    }

    @Preview(name = "TypeLabel Baseline", showBackground = true)
    @PreviewTest
    @Composable
    fun typeLabelBaseline() {
        AppTheme {
            Surface {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    PokemonTypesTheme(types = listOf(Type.Grass.toString())) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            TypeLabel(
                                text = "Grass",
                                colored = true,
                                metrics = TypeLabelMetrics.SMALL,
                            )
                            TypeLabel(
                                text = "Poison",
                                colored = false,
                                metrics = TypeLabelMetrics.SMALL,
                            )
                        }
                    }

                    PokemonTypesTheme(types = listOf(Type.Fire.toString())) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            TypeLabel(
                                text = "Fire",
                                colored = true,
                                metrics = TypeLabelMetrics.MEDIUM,
                            )
                            TypeLabel(
                                text = "Flying",
                                colored = false,
                                metrics = TypeLabelMetrics.MEDIUM,
                            )
                        }
                    }

                    PokemonTypesTheme(types = listOf(Type.Water.toString())) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            TypeLabel(
                                text = "Water",
                                colored = true,
                                metrics = TypeLabelMetrics.LARGE,
                            )
                            TypeLabel(
                                text = "Ice",
                                colored = false,
                                metrics = TypeLabelMetrics.LARGE,
                            )
                        }
                    }
                }
            }
        }
    }

    @Preview(name = "GenerationFilterChip Baseline", showBackground = true)
    @PreviewTest
    @Composable
    fun generationFilterChipBaseline() {
        AppTheme {
            Surface {
                Box(modifier = Modifier.size(width = 214.dp, height = 105.dp)) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        FilterGenerationItemScreenshotPreview(
                            generation = Generation.I,
                            selected = false,
                            index = 0,
                        )
                        FilterGenerationItemScreenshotPreview(
                            generation = Generation.II,
                            selected = true,
                            index = 1,
                        )
                    }
                }
            }
        }
    }

    @Preview(name = "TypeFilterChip Baseline", showBackground = true)
    @PreviewTest
    @Composable
    fun typeFilterChipBaseline() {
        AppTheme {
            Surface {
                Box(modifier = Modifier.size(width = 265.5.dp, height = 105.dp)) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        FilterTypeItemScreenshotPreview(
                            type = Type.Grass,
                            selected = false,
                            index = 0,
                        )
                        FilterTypeItemScreenshotPreview(
                            type = Type.Fire,
                            selected = true,
                            index = 1,
                        )
                    }
                }
            }
        }
    }
}
