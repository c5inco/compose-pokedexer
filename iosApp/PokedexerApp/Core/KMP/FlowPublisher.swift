import Foundation
import Combine
import Shared

// NOTE: With SKIE 0.10.8, this file is largely unused.
// SKIE automatically generates typed Swift flows (SkieSwiftFlow<T>, SkieSwiftOptionalFlow<T>)
// that conform to AsyncSequence, allowing direct iteration with `for await`.
//
// Example usage in ViewModels:
//   if let flow = sdk.getAllPokemon() {
//       for await pokemonList in flow {
//           // pokemonList is already typed as [Pokemon]
//       }
//   }
//
// The extension below is kept for backwards compatibility but should not be needed.

extension Kotlinx_coroutines_coreFlow {
    /// Converts a Kotlin Flow to a Swift AsyncThrowingStream.
    /// NOTE: With SKIE, prefer using the typed flow directly since it conforms to AsyncSequence.
    @available(*, deprecated, message: "Use SKIE's typed flows directly - they conform to AsyncSequence")
    func asAsyncSequence<T>() -> AsyncThrowingStream<T, Error> {
        // Wrap the raw Kotlinx_coroutines_coreFlow in SKIE's typed wrapper using AnyObject
        let skieFlow: SkieSwiftFlow<AnyObject> = SkieSwiftFlow(SkieKotlinFlow<AnyObject>(self))
        
        return AsyncThrowingStream { continuation in
            Task {
                for await value in skieFlow {
                    // Cast from AnyObject to the expected type (handles NSArray -> Swift Array bridging)
                    if let typedValue = value as? T {
                        continuation.yield(typedValue)
                    }
                }
                continuation.finish()
            }
        }
    }
}
