import Foundation
import shared

@MainActor
class PokemonListViewModel: ObservableObject {
    @Published var pokemon: [Pokemon] = []
    @Published var isLoading = false
    @Published var error: String?
    
    private let commonViewModel: CommonPokemonListViewModel
    
    init() {
        let sharedModule = IosSharedModule.shared.shared
        self.commonViewModel = sharedModule.createPokemonListViewModel(scope: IosSharedModule.shared.scope)
        
        // Subscribe to pokemon flow
        FlowUtilsKt.asWrapper(commonViewModel.pokemon).subscribe(
            scope: IosSharedModule.shared.scope,
            onEach: { [weak self] pokemon in
                self?.pokemon = pokemon as? [Pokemon] ?? []
            },
            onComplete: {},
            onThrow: { _ in }
        )
        
        // Subscribe to loading flow
        FlowUtilsKt.asWrapper(commonViewModel.isLoading).subscribe(
            scope: IosSharedModule.shared.scope,
            onEach: { [weak self] isLoading in
                self?.isLoading = isLoading as? Bool ?? false
            },
            onComplete: {},
            onThrow: { _ in }
        )
        
        // Subscribe to error flow
        FlowUtilsKt.asWrapper(commonViewModel.error).subscribe(
            scope: IosSharedModule.shared.scope,
            onEach: { [weak self] error in
                self?.error = error as? String
            },
            onComplete: {},
            onThrow: { _ in }
        )
    }
    
    func loadPokemon() {
        commonViewModel.refresh()
    }
}
