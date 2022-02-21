package des.c5inco.pokedexer.ui.pokedex

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import des.c5inco.pokedexer.ui.entity.PokemonListViewModel
import des.c5inco.pokedexer.ui.entity.PokemonRepository

@Preview
@Composable
fun PokemonListApiView() {
    val pokemonListViewModel = PokemonListViewModel(PokemonRepository())
    val lazyPokemonList = pokemonListViewModel.pokemons.collectAsLazyPagingItems()

    LazyColumn {
        items(lazyPokemonList) { pokemon ->
            pokemon?.let {
                it.name?.let { name ->
                    Text(name)
                }
            }
        }
    }
}