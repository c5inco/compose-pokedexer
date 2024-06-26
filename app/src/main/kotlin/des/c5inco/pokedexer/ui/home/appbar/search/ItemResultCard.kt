package des.c5inco.pokedexer.ui.home.appbar.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.data.items.SampleItems
import des.c5inco.pokedexer.model.Item
import des.c5inco.pokedexer.ui.common.ItemImage
import des.c5inco.pokedexer.ui.theme.AppTheme
import kotlin.math.exp

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ItemResultCard(
    modifier: Modifier = Modifier,
    item: Item = SampleItems.first(),
    animatedVisibilityScope: AnimatedVisibilityScope,
    onSelected: (Item) -> Unit = { }
) {
    Card(
        modifier = modifier
            .width(200.dp)
            .clickable { onSelected(item) }
            .sharedBounds(
                sharedContentState = rememberSharedContentState(key = "item-${item.id}-bounds"),
                animatedVisibilityScope = animatedVisibilityScope,
            ),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.sharedBounds(
                    sharedContentState = rememberSharedContentState(key = "item-name-${item.id}"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
            )
            ItemImage(
                item = item,
                modifier = Modifier.sharedElement(
                    state = rememberSharedContentState(key = "image-${item.id}"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
            )
        }
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
fun ItemResultCardPreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .padding(vertical = 20.dp)
        ) {
            SharedTransitionLayout {
                AnimatedContent(
                    targetState = true,
                ) { _ ->
                    LazyHorizontalGrid(
                        rows = GridCells.Fixed(3),
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .height(240.dp)
                            .fillMaxWidth()
                    ) {
                        items(5) {
                            ItemResultCard(
                                animatedVisibilityScope = this@AnimatedContent
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ItemResultCardExpanded(
    modifier: Modifier = Modifier,
    item: Item = SampleItems.last(),
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    Box {
        Card(
            modifier = modifier
                .width(300.dp)
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(key = "item-${item.id}-bounds"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    //clipInOverlayDuringTransition = OverlayClip(shapeForSharedElement)
                ),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 32.dp, start = 12.dp, end = 12.dp, bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.sharedBounds(
                        sharedContentState = rememberSharedContentState(key = "item-name-${item.id}"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
        ItemImage(
            item = item,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.TopCenter)
                .offset(y = -24.dp)
                .sharedElement(
                    state = rememberSharedContentState(key = "image-${item.id}"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
private fun ItemResultCardExpandedPreview() {
    var expanded by remember { mutableStateOf(false) }

    AppTheme {
        SharedTransitionLayout {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                AnimatedContent(
                    targetState = expanded,
                ) { targetState ->
                    if (targetState) {
                        ItemResultCardExpanded(
                            modifier = Modifier.clickable { expanded = false },
                            animatedVisibilityScope = this@AnimatedContent
                        )
                    } else {
                        ItemResultCard(
                            modifier = Modifier.clickable { expanded = true },
                            animatedVisibilityScope = this@AnimatedContent
                        )
                    }
                }
            }
        }
    }

}
