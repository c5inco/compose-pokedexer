package des.c5inco.pokedexer.ui.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.data.items.SampleItems
import des.c5inco.pokedexer.model.Item
import des.c5inco.pokedexer.ui.theme.AppTheme

@Composable
fun ItemsScreenRoute() {
}

@Composable
fun ItemsScreen(
    items: List<Item>
) {
    val context = LocalContext.current

    LazyColumn(
        contentPadding = PaddingValues(24.dp)
    ) {
        items(items) {
            val imageRes = context.resources.getIdentifier("item_${it.sprite}", "drawable", context.packageName)
            Row {
                Image(
                    painterResource(id = imageRes),
                    contentDescription = it.name,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.size(64.dp)
                )
                Text(it.name)
                Text(it.description)
            }
        }
    }
}

@Preview
@Composable
fun ItemsScreenPreview() {
    AppTheme {
        Surface {
            ItemsScreen(items = SampleItems)
        }
    }
}
