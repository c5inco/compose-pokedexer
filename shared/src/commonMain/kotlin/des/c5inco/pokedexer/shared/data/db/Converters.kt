package des.c5inco.pokedexer.shared.data.db

import androidx.room.TypeConverter
import des.c5inco.pokedexer.shared.model.Evolution
import des.c5inco.pokedexer.shared.model.EvolutionTrigger
import des.c5inco.pokedexer.shared.model.PokemonAbility
import des.c5inco.pokedexer.shared.model.PokemonMove

/**
 * Room TypeConverters for complex data types.
 */
class Converters {
    @TypeConverter
    fun stringToList(str: String?): List<String>? {
        return str?.split(",")
    }

    @TypeConverter
    fun listToString(list: List<String>?): String? {
        return list?.joinToString(",")
    }

    @TypeConverter
    fun stringToEvolutionList(str: String): List<Evolution> {
        val list = mutableListOf<Evolution>()

        if (str.isNotBlank()) {
            str.split("|").map {
                val evo = it.split(",")
                list.add(
                    Evolution(
                        id = evo[0].toInt(),
                        targetLevel = evo[1].toInt(),
                        trigger = EvolutionTrigger.fromInt(evo[2].toInt()),
                        itemId = evo[3].toInt()
                    )
                )
            }
        }

        return list.toList()
    }

    @TypeConverter
    fun evolutionListToString(list: List<Evolution>): String {
        return list.joinToString(separator = "|") {
            val data = listOf(it.id, it.targetLevel, it.trigger.value, it.itemId)
            data.joinToString(",")
        }
    }

    @TypeConverter
    fun stringToPokemonMoveList(str: String): List<PokemonMove> {
        val list = mutableListOf<PokemonMove>()

        if (str.isNotBlank()) {
            str.split("|").map {
                val move = it.split(",")
                list.add(PokemonMove(move[0].toInt(), move[1].toInt()))
            }
        }

        return list.toList()
    }

    @TypeConverter
    fun pokemonMoveListToString(list: List<PokemonMove>): String {
        return list.joinToString(separator = "|") {
            val data = listOf(it.id, it.targetLevel)
            data.joinToString(",")
        }
    }

    @TypeConverter
    fun stringToPokemonAbilityList(str: String): List<PokemonAbility> {
        val list = mutableListOf<PokemonAbility>()

        if (str.isNotBlank()) {
            str.split("|").map {
                val ability = it.split(",")
                list.add(PokemonAbility(ability[0].toInt(), ability[1].toBoolean()))
            }
        }

        return list.toList()
    }

    @TypeConverter
    fun pokemonAbilityListToString(list: List<PokemonAbility>): String {
        return list.joinToString(separator = "|") {
            val data = listOf(it.id, it.isHidden)
            data.joinToString(",")
        }
    }
}

