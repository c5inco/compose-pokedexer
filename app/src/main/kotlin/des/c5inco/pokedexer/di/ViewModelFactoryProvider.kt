package des.c5inco.pokedexer.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import des.c5inco.pokedexer.ui.pokedex.PokemonDetailsViewModel

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ViewModelFactoryProvider {
    fun pokemonDetailsViewModelFactory(): PokemonDetailsViewModel.PokemonDetailsViewModelFactory
}