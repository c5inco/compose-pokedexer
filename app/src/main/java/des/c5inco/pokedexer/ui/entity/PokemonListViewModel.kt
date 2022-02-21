package des.c5inco.pokedexer.ui.entity

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

class PokemonListViewModel(private val repository: PokemonRepository): ViewModel() {

    val pokemons: Flow<PagingData<Pokemon>> = Pager(PagingConfig(pageSize = 20)) {
        PokemonDataSource(repository)
    }.flow
}