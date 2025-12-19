package des.c5inco.pokedexer.shared.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

class FlowWrapper<T>(private val flow: Flow<T>) {
    fun subscribe(
        scope: CoroutineScope,
        onEach: (T) -> Unit,
        onComplete: () -> Unit,
        onThrow: (Throwable) -> Unit
    ): Job = flow
        .onEach { onEach(it) }
        .onCompletion { onComplete() }
        .catch { onThrow(it) }
        .launchIn(scope)
}

fun <T> Flow<T>.asWrapper(): FlowWrapper<T> = FlowWrapper(this)
