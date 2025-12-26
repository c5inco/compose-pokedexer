import Foundation
import Combine
import shared

/// Extension to convert Kotlin Flow to Swift AsyncThrowingStream
/// This enables seamless use of Kotlin Flows in Swift async/await code
extension Kotlinx_coroutines_coreFlow {
    /// Converts a Kotlin Flow to a Swift AsyncThrowingStream
    /// Usage: for try await value in flow.asAsyncSequence() as AsyncThrowingStream<Type, Error> { ... }
    func asAsyncSequence<T>() -> AsyncThrowingStream<T, Error> {
        print("🟡 FlowPublisher: Creating AsyncThrowingStream for type \\(T.self)")
        
        return AsyncThrowingStream { continuation in
            print("🟡 FlowPublisher: AsyncThrowingStream continuation started")
            
            let collector = SwiftFlowCollector<T> { value in
                print("🟡 FlowPublisher: Collector received value of type \\(type(of: value))")
                continuation.yield(value)
            }
            
            print("🟡 FlowPublisher: About to call collect(collector:completionHandler:)")
            self.collect(collector: collector) { error in
                print("🟡 FlowPublisher: Completion handler called with error: \\(String(describing: error))")
                if let error = error {
                    print("🔴 FlowPublisher: Error in flow: \\(error)")
                    continuation.finish(throwing: error)
                } else {
                    print("🟡 FlowPublisher: Flow completed successfully")
                    continuation.finish()
                }
            }
            print("🟡 FlowPublisher: collect() call returned")
        }
    }
}

/// Flow collector implementation that conforms to Kotlin's FlowCollector
private class SwiftFlowCollector<T>: Kotlinx_coroutines_coreFlowCollector {
    let onEmit: (T) -> Void
    
    init(onEmit: @escaping (T) -> Void) {
        self.onEmit = onEmit
    }
    
    func emit(value: Any?, completionHandler: @escaping (Error?) -> Void) {
        print("🟡 FlowCollector: emit() called with value type: \\(type(of: value))")
        if let typedValue = value as? T {
            print("🟡 FlowCollector: Successfully cast to \\(T.self), yielding value")
            onEmit(typedValue)
        } else {
            print("🔴 FlowCollector: Failed to cast \\(type(of: value)) to \\(T.self)")
        }
        completionHandler(nil)
        print("🟡 FlowCollector: emit() completion handler called")
    }
}
