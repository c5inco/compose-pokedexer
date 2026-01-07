//
//  FilterSheet.swift
//  PokedexerApp
//
//  Created by Chris Sinco on 12/30/25.
//

import Shared
import SwiftUI

// MARK: - Filter Sheet
struct FilterSheet: View {
    @ObservedObject var viewModel: PokedexListViewModel
    @Environment(\.dismiss) var dismiss

    let generations = Array(1...9)

    var body: some View {
        NavigationView {
            List {
                Toggle("Show Favorites Only", isOn: $viewModel.showFavorites)
                
                Section {
                    Picker("By Generation", selection: $viewModel.selectedGeneration) {
                        ForEach(generations, id: \.self) { gen in
                            Text("Generation \(gen)")
                        }
                    }
                    .pickerStyle(.menu)
                    .onChange(of: viewModel.selectedGeneration) {
                        viewModel.reloadForCurrentGeneration()
                    }
                }

                Section {
                    FlowLayout(spacing: 8) {
                        ForEach(PokemonType.allCases, id: \.self) { type in
                            let typeName = type.name.capitalized
                            let isSelected = viewModel.selectedTypes.contains(typeName)
                            
                            Button(action: {
                                viewModel.toggleType(typeName)
                            }) {
                                TypeLabel(
                                    typeName: typeName,
                                    size: .large,
                                    colored: true,
                                    showIcon: true,
                                    isSelected: isSelected
                                )
                                .pokemonTheme(type: type)
                            }
                            .buttonStyle(.plain)
                        }
                    }
                    .padding(.vertical, 8)
                } header: {
                    HStack {
                        Text("By Type")
                        Spacer()
                        if !viewModel.selectedTypes.isEmpty {
                            Button("Clear") {
                                viewModel.clearTypeFilters()
                            }
                            .foregroundColor(.accentColor)
                        }
                    }
                }
            }
            .navigationTitle("Filters")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button(action: { dismiss() }) {
                        Label("Done", systemImage: "checkmark")
                    }
                }
            }
        }
    }
}
