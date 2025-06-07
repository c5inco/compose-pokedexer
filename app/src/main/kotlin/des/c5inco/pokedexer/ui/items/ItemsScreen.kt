package des.c5inco.pokedexer.ui.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import des.c5inco.pokedexer.R
import des.c5inco.pokedexer.data.items.SampleItems
import des.c5inco.pokedexer.model.Item
import des.c5inco.pokedexer.ui.common.ItemImage
import des.c5inco.pokedexer.ui.theme.AppTheme

@Composable
fun ItemsScreenRoute(
    viewModel: ItemsViewModel,
    onBackClick: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ItemsScreen(
        state = state,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemsScreen(
    state: ItemsListUiState,
    onBackClick: () -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text(stringResource(R.string.itemsLabel)) },
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        Column(
            Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            when (val s = state) {
                is ItemsListUiState.Ready -> {
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Adaptive(120.dp),
                        verticalItemSpacing = 4.dp,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        contentPadding = PaddingValues(
                            top = 12.dp,
                            bottom = 12.dp + WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
                        ),
                        content = {
                            items(s.items) {
                                ItemCard(item = it)
                            }
                        },
                    )
                }
                is ItemsListUiState.Loading -> {
                    CircularProgressIndicator()
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
            modifier = Modifier.padding(20.dp),
        ) {
            ItemImage(
                item = item,
                modifier = Modifier.size(48.dp)
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview
@Composable
fun ItemsScreenPreview() {
    val state: ItemsListUiState = ItemsListUiState.Ready(
        items = SampleItems
    )

    AppTheme {
        Surface {
            ItemsScreen(state)
        }
    }
}
