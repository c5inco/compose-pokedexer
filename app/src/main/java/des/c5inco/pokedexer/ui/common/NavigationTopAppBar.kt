package des.c5inco.pokedexer.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Icon as M3Icon
import androidx.compose.material3.IconButton as M3IconButton
import androidx.compose.material3.LocalContentColor as M3LocalContentColor
import androidx.compose.material3.MaterialTheme as M3Theme
import androidx.compose.material3.ProvideTextStyle as M3ProvideTextStyle

@Composable
fun NavigationTopAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    contentColor: Color = M3LocalContentColor.current,
    onBackClick: () -> Unit = {}
) {
    Box(
        modifier
            .pointerInput(Unit) {}
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 0.dp, start = 12.dp, end = 12.dp)
    ) {
        CompositionLocalProvider(M3LocalContentColor provides contentColor) {
            M3IconButton(
                modifier = Modifier.align(Alignment.CenterStart),
                onClick = { onBackClick() }
            ) {
                M3Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                )
            }

            Row(
                modifier = Modifier.align(Alignment.Center),
            ) {
                M3ProvideTextStyle(
                    value = M3Theme.typography.titleLarge,
                    content = title
                )
            }

            Row(
                modifier = Modifier.align(Alignment.CenterEnd),
                content = actions
            )
        }
    }
}