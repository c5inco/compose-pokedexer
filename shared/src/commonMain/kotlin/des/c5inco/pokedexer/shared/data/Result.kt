package des.c5inco.pokedexer.shared.data

/**
 * A generic class that holds a value or an exception
 */
sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

fun <T> Result<T>.successOr(fallback: T): T {
    return (this as? Result.Success<T>)?.data ?: fallback
}

fun <T> Result<T>.dataOrThrow(): T = when (this) {
    is Result.Success -> data
    is Result.Error -> throw exception
}
