package des.c5inco.pokedexer.ui.home.appbar.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import des.c5inco.pokedexer.R
import des.c5inco.pokedexer.ui.theme.PokedexerTheme

@Composable
fun RoundedSearchBar() {
    Surface(
        color = MaterialTheme.colors.background,
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.search),
                contentDescription = "Search icon",
            )
            Text(
                "Search Pokemon, Move, Ability, etc",
                fontSize = 14.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@Preview
@Composable
fun RoundedSearchBarPreview() {
    PokedexerTheme {
        RoundedSearchBar()
    }
}