package des.c5inco.pokedexer.ui.home.appbar.elements

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.TextFieldLineLimits
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.clearText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.ui.theme.AppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RoundedSearchBar(
    modifier: Modifier = Modifier,
    searchText: TextFieldState,
) {
    var showPlaceholder by remember { mutableStateOf(searchText.text.isEmpty()) }

    Surface(
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Row(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BasicTextField2(
                modifier = Modifier
                    .weight(1f)
                    .onFocusEvent {
                        if (searchText.text.isEmpty()) {
                            showPlaceholder = !it.isFocused
                        }
                    },
                state = searchText,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = contentColorFor(backgroundColor = MaterialTheme.colorScheme.surfaceVariant),
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onPrimaryContainer),
                lineLimits = TextFieldLineLimits.SingleLine,
                decorator = { innerTextField ->
                    Row(
                        modifier = Modifier.padding(end = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        if (showPlaceholder) {
                            Text(
                                "Search Pokemon, Move, Ability, etc",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 12.dp),
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        } else {
                            innerTextField()
                        }
                    }
                }
            )
            if (searchText.text.isNotEmpty()) {
                IconButton(
                    onClick = { searchText.clearText() },
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear Search",
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview
@Composable
fun RoundedSearchBarPreview() {
    AppTheme {
        RoundedSearchBar(
            searchText = TextFieldState()
        )
    }
}