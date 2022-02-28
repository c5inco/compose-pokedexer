package des.c5inco.pokedexer.ui.home

import androidx.lifecycle.ViewModel
import coil.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    val imageLoader: ImageLoader
): ViewModel()