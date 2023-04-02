package des.c5inco.pokedexer.ui.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.skydoves.landscapist.coil.CoilImage
import des.c5inco.pokedexer.R
import des.c5inco.pokedexer.data.items.SampleItems
import des.c5inco.pokedexer.model.Item
import des.c5inco.pokedexer.ui.common.Emphasis
import des.c5inco.pokedexer.ui.common.NavigationTopAppBar
import des.c5inco.pokedexer.ui.common.PokeBall
import des.c5inco.pokedexer.ui.common.itemAssetsUri
import des.c5inco.pokedexer.ui.theme.AppTheme

@Composable
fun ItemsScreenRoute(
    viewModel: ItemsListViewModel,
    onBackClick: () -> Unit = {}
) {
    ItemsScreen(
        loading = viewModel.uiState.loading,
        items = viewModel.uiState.items,
        onBackClick = onBackClick
    )
}

@Composable
fun ItemsScreen(
    loading: Boolean = false,
    items: List<Item> = SampleItems,
    onBackClick: () -> Unit = {}
) {
    Surface {
        Column(
            Modifier.fillMaxSize()
        ) {
            NavigationTopAppBar(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(top = 16.dp),
                onBackClick = onBackClick
            )
            Column(
                Modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    text = "Items",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(
                        top = 16.dp, bottom = 24.dp
                    )
                )
                if (loading) {
                    CircularProgressIndicator()
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = WindowInsets.navigationBars.asPaddingValues()
                    ) {
                        items(items) {
                            ItemCard(item = it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ItemCard(
    modifier: Modifier = Modifier,
    item: Item
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                CoilImage(
                    imageModel = { itemAssetsUri(item.sprite) },
                    previewPlaceholder = R.drawable.item_flame_orb,
                    success = { imageState ->
                        imageState.drawable?.let {
                            Image(
                                bitmap = it.toBitmap().asImageBitmap(),
                                contentScale = ContentScale.Inside,
                                contentDescription = null,
                                modifier = Modifier.matchParentSize()
                            )
                        }
                    },
                    failure = {
                        PokeBall(
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            alpha = Emphasis.Disabled.alpha
                        )
                    },
                    modifier = Modifier.size(48.dp)
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleLarge.copy(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    ),
                )
            }
            Text(
                text = item.description,
                modifier = Modifier.padding(start = 60.dp)
            )
        }
    }
}

@Preview
@Composable
fun ItemsScreenPreview() {
    AppTheme {
        Surface {
            ItemsScreen()
        }
    }
}
