package des.c5inco.pokedexer.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import des.c5inco.pokedexer.ui.pokedex.PokemonDetailsViewModel

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {
    fun pokemonDetailsViewModelFactory() : PokemonDetailsViewModel.PokemonDetailsViewModelFactory
}