package des.c5inco.pokedexer.shared

/**
 * Platform-specific information interface.
 * Used to verify KMP setup is working correctly.
 */
expect fun getPlatformName(): String

class Greeting {
    fun greet(): String = "Hello from ${getPlatformName()}!"
}

