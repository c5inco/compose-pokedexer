package des.c5inco.pokedexer.di

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import des.c5inco.pokedexer.PokedexerApplication
import des.c5inco.pokedexer.RootViewModel
import des.c5inco.pokedexer.ui.home.HomeViewModel
import des.c5inco.pokedexer.ui.items.ItemsViewModel
import des.c5inco.pokedexer.ui.moves.MovesListViewModel
import des.c5inco.pokedexer.ui.pokedex.PokedexViewModel

/**
 * Composable helper to get a ViewModel from the Metro graph.
 * This is a replacement for hiltViewModel() from Hilt.
 *
 * For ViewModels provided directly by the graph (no runtime params), use:
 * val vm: MyViewModel = metroViewModel()
 *
 * For ViewModels requiring assisted injection with runtime params, use:
 * val vm: MyViewModel = metroViewModel { pokedexViewModelFactory.create(param) }
 */
@Composable
inline fun <reified T : ViewModel> metroViewModel(
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    },
    key: String? = null,
    crossinline initializer: ApplicationGraph.(CreationExtras) -> T = { extras ->
        when (T::class) {
            RootViewModel::class -> rootViewModel
            HomeViewModel::class -> homeViewModel
            MovesListViewModel::class -> movesListViewModel
            ItemsViewModel::class -> itemsViewModel
            PokedexViewModel::class -> pokedexViewModelFactory.create(extras.createSavedStateHandle())
            else -> throw IllegalArgumentException(
                "Unknown ViewModel class: ${T::class.java.name}. " +
                "If it requires assisted parameters (other than SavedStateHandle), " +
                "provide an initializer block."
            )
        } as T
    }
): T {
    val context = LocalContext.current
    val appGraph = (context.applicationContext as PokedexerApplication).appGraph

    return viewModel(
        viewModelStoreOwner = viewModelStoreOwner,
        key = key,
        initializer = { initializer(appGraph, this) }
    )
}
