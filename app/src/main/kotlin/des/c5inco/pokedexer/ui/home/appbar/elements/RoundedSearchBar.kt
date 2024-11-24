package des.c5inco.pokedexer.ui.home.appbar.elements

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.R
import des.c5inco.pokedexer.ui.theme.AppTheme

@Composable
fun RoundedSearchBar(
    modifier: Modifier = Modifier,
    searchText: TextFieldState,
    onTextClear: () -> Unit = {}
) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BasicTextField(
                modifier = Modifier.weight(1f),
                state = searchText,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = contentColorFor(backgroundColor = MaterialTheme.colorScheme.surfaceVariant),
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onPrimaryContainer),
                lineLimits = TextFieldLineLimits.SingleLine,
                decorator = { innerTextField ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Box {
                            innerTextField()

                            if (searchText.text.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.searchPlaceholderText),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.outline,
                                    modifier = Modifier.padding(end = 12.dp),
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            )
            if (searchText.text.isNotEmpty()) {
                IconButton(
                    onClick = { onTextClear() },
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(R.string.clearSearchContentDescription),
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun RoundedSearchBarPreview() {
    AppTheme {
        RoundedSearchBar(
            searchText = TextFieldState()
        )
    }
}