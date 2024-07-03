package des.c5inco.pokedexer.ui.home.appbar.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.data.moves.SampleMoves
import des.c5inco.pokedexer.model.Move
import des.c5inco.pokedexer.model.category
import des.c5inco.pokedexer.model.type
import des.c5inco.pokedexer.ui.common.CategoryIcon
import des.c5inco.pokedexer.ui.common.TypeIconLabel
import des.c5inco.pokedexer.ui.theme.AppTheme
import des.c5inco.pokedexer.ui.theme.MoveCategoryTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MoveResultCard(
    modifier: Modifier = Modifier,
    move: Move = SampleMoves.first(),
    animatedVisibilityScope: AnimatedVisibilityScope,
    onSelected: (Move) -> Unit = { }
) {
    MoveCategoryTheme(
        category = move.category()
    ) {
        Card(
            modifier = modifier
                .width(200.dp)
                .clickable { onSelected(move) }
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(key = "move-${move.id}-bounds"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    clipInOverlayDuringTransition = OverlayClip(MaterialTheme.shapes.large)
                ),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, top = 12.dp, bottom = 12.dp, end = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = move.name,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.sharedBounds(
                        sharedContentState = rememberSharedContentState(key = "move-name-${move.id}"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = textBoundsTransform,
                    )
                )
                CategoryIcon(
                    move = move,
                    modifier = Modifier
                        .size(36.dp)
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState(key = "category-icon-${move.id}"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = imageBoundsTransform,
                        )
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@PreviewLightDark
@Composable
fun MoveResultCardPreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(vertical = 20.dp)
        ) {
            SharedTransitionLayout {
                AnimatedVisibility(
                    visible = true
                ) {
                    LazyHorizontalGrid(
                        rows = GridCells.Fixed(4),
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .height(240.dp)
                            .fillMaxWidth()
                    ) {
                        items(5) {
                            MoveResultCard(
                                move = SampleMoves[it],
                                animatedVisibilityScope = this@AnimatedVisibility
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
fun SharedTransitionScope.MoveResultExpandedCard(
    modifier: Modifier = Modifier,
    move: Move = SampleMoves.first(),
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    MoveCategoryTheme(
        category = move.category()
    ) {
        Card(
            modifier = modifier
                .width(260.dp)
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(key = "move-${move.id}-bounds"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = containerBoundsTransform,
                    clipInOverlayDuringTransition = OverlayClip(MaterialTheme.shapes.large)
                ),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            )
        ) {
            Box {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                ) {
                    Text(
                        text = move.name,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MoveCategoryTheme.colorScheme.primary,
                        modifier = Modifier.sharedBounds(
                            sharedContentState = rememberSharedContentState(key = "move-name-${move.id}"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = textBoundsTransform
                        )
                    )
                    Spacer(Modifier.height(6.dp))
                    with(animatedVisibilityScope) {
                        Column(
                            Modifier.animateEnterExit(
                                    enter = fadeIn(tween(400)) + slideInVertically(tween(300)) { -it / 2 },
                                    exit = fadeOut()
                                )
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TypeIconLabel(type = move.type())
                                Spacer(Modifier.width(6.dp))
                                ValuesText(
                                    label = "PP",
                                    value = move.pp,
                                    modifier = Modifier.weight(0.75f)
                                )
                                ValuesText(
                                    label = "PWR",
                                    value = move.power ?: -1,
                                )
                                ValuesText(
                                    label = "ACC",
                                    value = move.accuracy ?: -1,
                                )
                            }
                            Spacer(Modifier.height(12.dp))
                            Text(
                                text = move.description,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                }
                CategoryIcon(
                    move = move,
                    modifier = Modifier
                        .graphicsLayer { alpha = 0.4f }
                        .size(64.dp)
                        .align(Alignment.TopEnd)
                        .offset(x = 16.dp, y = (-16).dp)
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState(key = "category-icon-${move.id}"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = imageBoundsTransform,
                        )
                )
            }
        }
    }
}

@Composable
private fun RowScope.ValuesText(
    modifier: Modifier = Modifier.weight(1f),
    label: String,
    value: Int
) {
    Row(
        modifier = modifier
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.graphicsLayer { alpha = 0.7f }
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = if (value == -1) "-" else value.toString(),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@PreviewLightDark
@Composable
private fun MoveResultExpandedCardPreview() {
    AppTheme {
        SharedTransitionLayout {
            AnimatedVisibility(
                visible = true
            ) {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    MoveResultExpandedCard(
                        move = SampleMoves[3],
                        animatedVisibilityScope = this@AnimatedVisibility
                    )
                    MoveResultExpandedCard(
                        move = SampleMoves[1],
                        animatedVisibilityScope = this@AnimatedVisibility
                    )
                    MoveResultExpandedCard(
                        move = SampleMoves[2],
                        animatedVisibilityScope = this@AnimatedVisibility
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
private fun MoveResultTransitionPreview() {
    var expanded by remember { mutableStateOf(false) }
    val sampleMove = SampleMoves[3]

    AppTheme {
        SharedTransitionLayout {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .fillMaxSize()
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                AnimatedContent(
                    targetState = expanded,
                ) { targetState ->
                    if (targetState) {
                        MoveResultExpandedCard(
                            move = sampleMove,
                            modifier = Modifier.clickable { expanded = false },
                            animatedVisibilityScope = this@AnimatedContent
                        )
                    } else {
                        MoveResultCard(
                            move = sampleMove,
                            animatedVisibilityScope = this@AnimatedContent,
                            onSelected = { expanded = !expanded }
                        )
                    }
                }
            }
        }
    }
}