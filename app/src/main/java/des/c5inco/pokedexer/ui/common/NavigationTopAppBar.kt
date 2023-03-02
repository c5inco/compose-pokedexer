package des.c5inco.pokedexer.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun NavigationTopAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    contentColor: Color = LocalContentColor.current,
    onBackClick: () -> Unit = {}
) {
    Box(
        modifier
            .pointerInput(Unit) {}
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 0.dp, start = 12.dp, end = 12.dp)
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            IconButton(
                modifier = Modifier.align(Alignment.CenterStart),
                onClick = { onBackClick() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                )
            }

            Row(
                modifier = Modifier.align(Alignment.Center),
            ) {
                ProvideTextStyle(
                    value = MaterialTheme.typography.titleLarge,
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