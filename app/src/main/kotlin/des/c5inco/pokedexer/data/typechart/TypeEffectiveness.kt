package des.c5inco.pokedexer.data.typechart

import des.c5inco.pokedexer.model.Type

/**
 * Type effectiveness multipliers for Pokemon battles.
 * The outer map key is the attacking type, the inner map key is the defending type.
 * Values: 2.0 = Super Effective, 0.5 = Not Very Effective, 0.0 = No Effect, 1.0 = Neutral
 */
object TypeEffectiveness {
    // Order of types as displayed in the chart (matches the wireframe)
    val displayOrder = listOf(
        Type.Normal, Type.Fire, Type.Water, Type.Grass, Type.Electric, Type.Ice,
        Type.Fighting, Type.Poison, Type.Ground, Type.Flying, Type.Psychic, Type.Bug,
        Type.Rock, Type.Ghost, Type.Dragon, Type.Steel, Type.Dark, Type.Fairy
    )

    private val chart: Map<Type, Map<Type, Float>> = mapOf(
        Type.Normal to mapOf(
            Type.Normal to 1f, Type.Fire to 1f, Type.Water to 1f, Type.Grass to 1f,
            Type.Electric to 1f, Type.Ice to 1f, Type.Fighting to 1f, Type.Poison to 1f,
            Type.Ground to 1f, Type.Flying to 1f, Type.Psychic to 1f, Type.Bug to 1f,
            Type.Rock to 0.5f, Type.Ghost to 0f, Type.Dragon to 1f, Type.Steel to 0.5f,
            Type.Dark to 1f, Type.Fairy to 1f
        ),
        Type.Fire to mapOf(
            Type.Normal to 1f, Type.Fire to 0.5f, Type.Water to 0.5f, Type.Grass to 2f,
            Type.Electric to 1f, Type.Ice to 2f, Type.Fighting to 1f, Type.Poison to 1f,
            Type.Ground to 1f, Type.Flying to 1f, Type.Psychic to 1f, Type.Bug to 2f,
            Type.Rock to 0.5f, Type.Ghost to 1f, Type.Dragon to 0.5f, Type.Steel to 2f,
            Type.Dark to 1f, Type.Fairy to 1f
        ),
        Type.Water to mapOf(
            Type.Normal to 1f, Type.Fire to 2f, Type.Water to 0.5f, Type.Grass to 0.5f,
            Type.Electric to 1f, Type.Ice to 1f, Type.Fighting to 1f, Type.Poison to 1f,
            Type.Ground to 2f, Type.Flying to 1f, Type.Psychic to 1f, Type.Bug to 1f,
            Type.Rock to 2f, Type.Ghost to 1f, Type.Dragon to 0.5f, Type.Steel to 1f,
            Type.Dark to 1f, Type.Fairy to 1f
        ),
        Type.Grass to mapOf(
            Type.Normal to 1f, Type.Fire to 0.5f, Type.Water to 2f, Type.Grass to 0.5f,
            Type.Electric to 1f, Type.Ice to 1f, Type.Fighting to 1f, Type.Poison to 0.5f,
            Type.Ground to 2f, Type.Flying to 0.5f, Type.Psychic to 1f, Type.Bug to 0.5f,
            Type.Rock to 2f, Type.Ghost to 1f, Type.Dragon to 0.5f, Type.Steel to 0.5f,
            Type.Dark to 1f, Type.Fairy to 1f
        ),
        Type.Electric to mapOf(
            Type.Normal to 1f, Type.Fire to 1f, Type.Water to 2f, Type.Grass to 0.5f,
            Type.Electric to 0.5f, Type.Ice to 1f, Type.Fighting to 1f, Type.Poison to 1f,
            Type.Ground to 0f, Type.Flying to 2f, Type.Psychic to 1f, Type.Bug to 1f,
            Type.Rock to 1f, Type.Ghost to 1f, Type.Dragon to 0.5f, Type.Steel to 1f,
            Type.Dark to 1f, Type.Fairy to 1f
        ),
        Type.Ice to mapOf(
            Type.Normal to 1f, Type.Fire to 0.5f, Type.Water to 0.5f, Type.Grass to 2f,
            Type.Electric to 1f, Type.Ice to 0.5f, Type.Fighting to 1f, Type.Poison to 1f,
            Type.Ground to 2f, Type.Flying to 2f, Type.Psychic to 1f, Type.Bug to 1f,
            Type.Rock to 1f, Type.Ghost to 1f, Type.Dragon to 2f, Type.Steel to 0.5f,
            Type.Dark to 1f, Type.Fairy to 1f
        ),
        Type.Fighting to mapOf(
            Type.Normal to 2f, Type.Fire to 1f, Type.Water to 1f, Type.Grass to 1f,
            Type.Electric to 1f, Type.Ice to 2f, Type.Fighting to 1f, Type.Poison to 0.5f,
            Type.Ground to 1f, Type.Flying to 0.5f, Type.Psychic to 0.5f, Type.Bug to 0.5f,
            Type.Rock to 2f, Type.Ghost to 0f, Type.Dragon to 1f, Type.Steel to 2f,
            Type.Dark to 2f, Type.Fairy to 0.5f
        ),
        Type.Poison to mapOf(
            Type.Normal to 1f, Type.Fire to 1f, Type.Water to 1f, Type.Grass to 2f,
            Type.Electric to 1f, Type.Ice to 1f, Type.Fighting to 1f, Type.Poison to 0.5f,
            Type.Ground to 0.5f, Type.Flying to 1f, Type.Psychic to 1f, Type.Bug to 1f,
            Type.Rock to 0.5f, Type.Ghost to 0.5f, Type.Dragon to 1f, Type.Steel to 0f,
            Type.Dark to 1f, Type.Fairy to 2f
        ),
        Type.Ground to mapOf(
            Type.Normal to 1f, Type.Fire to 2f, Type.Water to 1f, Type.Grass to 0.5f,
            Type.Electric to 2f, Type.Ice to 1f, Type.Fighting to 1f, Type.Poison to 2f,
            Type.Ground to 1f, Type.Flying to 0f, Type.Psychic to 1f, Type.Bug to 0.5f,
            Type.Rock to 2f, Type.Ghost to 1f, Type.Dragon to 1f, Type.Steel to 2f,
            Type.Dark to 1f, Type.Fairy to 1f
        ),
        Type.Flying to mapOf(
            Type.Normal to 1f, Type.Fire to 1f, Type.Water to 1f, Type.Grass to 2f,
            Type.Electric to 0.5f, Type.Ice to 1f, Type.Fighting to 2f, Type.Poison to 1f,
            Type.Ground to 1f, Type.Flying to 1f, Type.Psychic to 1f, Type.Bug to 2f,
            Type.Rock to 0.5f, Type.Ghost to 1f, Type.Dragon to 1f, Type.Steel to 0.5f,
            Type.Dark to 1f, Type.Fairy to 1f
        ),
        Type.Psychic to mapOf(
            Type.Normal to 1f, Type.Fire to 1f, Type.Water to 1f, Type.Grass to 1f,
            Type.Electric to 1f, Type.Ice to 1f, Type.Fighting to 2f, Type.Poison to 2f,
            Type.Ground to 1f, Type.Flying to 1f, Type.Psychic to 0.5f, Type.Bug to 1f,
            Type.Rock to 1f, Type.Ghost to 1f, Type.Dragon to 1f, Type.Steel to 0.5f,
            Type.Dark to 0f, Type.Fairy to 1f
        ),
        Type.Bug to mapOf(
            Type.Normal to 1f, Type.Fire to 0.5f, Type.Water to 1f, Type.Grass to 2f,
            Type.Electric to 1f, Type.Ice to 1f, Type.Fighting to 0.5f, Type.Poison to 0.5f,
            Type.Ground to 1f, Type.Flying to 0.5f, Type.Psychic to 2f, Type.Bug to 1f,
            Type.Rock to 1f, Type.Ghost to 0.5f, Type.Dragon to 1f, Type.Steel to 0.5f,
            Type.Dark to 2f, Type.Fairy to 0.5f
        ),
        Type.Rock to mapOf(
            Type.Normal to 1f, Type.Fire to 2f, Type.Water to 1f, Type.Grass to 1f,
            Type.Electric to 1f, Type.Ice to 2f, Type.Fighting to 0.5f, Type.Poison to 1f,
            Type.Ground to 0.5f, Type.Flying to 2f, Type.Psychic to 1f, Type.Bug to 2f,
            Type.Rock to 1f, Type.Ghost to 1f, Type.Dragon to 1f, Type.Steel to 0.5f,
            Type.Dark to 1f, Type.Fairy to 1f
        ),
        Type.Ghost to mapOf(
            Type.Normal to 0f, Type.Fire to 1f, Type.Water to 1f, Type.Grass to 1f,
            Type.Electric to 1f, Type.Ice to 1f, Type.Fighting to 1f, Type.Poison to 1f,
            Type.Ground to 1f, Type.Flying to 1f, Type.Psychic to 2f, Type.Bug to 1f,
            Type.Rock to 1f, Type.Ghost to 2f, Type.Dragon to 1f, Type.Steel to 1f,
            Type.Dark to 0.5f, Type.Fairy to 1f
        ),
        Type.Dragon to mapOf(
            Type.Normal to 1f, Type.Fire to 1f, Type.Water to 1f, Type.Grass to 1f,
            Type.Electric to 1f, Type.Ice to 1f, Type.Fighting to 1f, Type.Poison to 1f,
            Type.Ground to 1f, Type.Flying to 1f, Type.Psychic to 1f, Type.Bug to 1f,
            Type.Rock to 1f, Type.Ghost to 1f, Type.Dragon to 2f, Type.Steel to 0.5f,
            Type.Dark to 1f, Type.Fairy to 0f
        ),
        Type.Steel to mapOf(
            Type.Normal to 1f, Type.Fire to 0.5f, Type.Water to 0.5f, Type.Grass to 1f,
            Type.Electric to 0.5f, Type.Ice to 2f, Type.Fighting to 1f, Type.Poison to 1f,
            Type.Ground to 1f, Type.Flying to 1f, Type.Psychic to 1f, Type.Bug to 1f,
            Type.Rock to 2f, Type.Ghost to 1f, Type.Dragon to 1f, Type.Steel to 0.5f,
            Type.Dark to 1f, Type.Fairy to 2f
        ),
        Type.Dark to mapOf(
            Type.Normal to 1f, Type.Fire to 1f, Type.Water to 1f, Type.Grass to 1f,
            Type.Electric to 1f, Type.Ice to 1f, Type.Fighting to 0.5f, Type.Poison to 1f,
            Type.Ground to 1f, Type.Flying to 1f, Type.Psychic to 2f, Type.Bug to 1f,
            Type.Rock to 1f, Type.Ghost to 2f, Type.Dragon to 1f, Type.Steel to 1f,
            Type.Dark to 0.5f, Type.Fairy to 0.5f
        ),
        Type.Fairy to mapOf(
            Type.Normal to 1f, Type.Fire to 0.5f, Type.Water to 1f, Type.Grass to 1f,
            Type.Electric to 1f, Type.Ice to 1f, Type.Fighting to 2f, Type.Poison to 0.5f,
            Type.Ground to 1f, Type.Flying to 1f, Type.Psychic to 1f, Type.Bug to 1f,
            Type.Rock to 1f, Type.Ghost to 1f, Type.Dragon to 2f, Type.Steel to 0.5f,
            Type.Dark to 2f, Type.Fairy to 1f
        )
    )

    /**
     * Get the effectiveness multiplier for an attacking type against a defending type.
     */
    fun getEffectiveness(attacker: Type, defender: Type): Float {
        return chart[attacker]?.get(defender) ?: 1f
    }
}
