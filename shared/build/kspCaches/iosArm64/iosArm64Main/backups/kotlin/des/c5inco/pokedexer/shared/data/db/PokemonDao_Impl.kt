package des.c5inco.pokedexer.shared.`data`.db

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.appendPlaceholders
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import des.c5inco.pokedexer.shared.model.Evolution
import des.c5inco.pokedexer.shared.model.Pokemon
import des.c5inco.pokedexer.shared.model.PokemonAbility
import des.c5inco.pokedexer.shared.model.PokemonMove
import kotlin.Double
import kotlin.Int
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlin.text.StringBuilder
import kotlinx.coroutines.flow.Flow

@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class PokemonDao_Impl(
  __db: RoomDatabase,
) : PokemonDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfPokemon: EntityInsertAdapter<Pokemon>

  private val __converters: Converters = Converters()

  private val __deleteAdapterOfPokemon: EntityDeleteOrUpdateAdapter<Pokemon>
  init {
    this.__db = __db
    this.__insertAdapterOfPokemon = object : EntityInsertAdapter<Pokemon>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `Pokemon` (`id`,`name`,`description`,`types`,`category`,`image`,`height`,`weight`,`genderRate`,`generationId`,`hp`,`attack`,`defense`,`specialAttack`,`specialDefense`,`speed`,`evolutions`,`moves`,`abilities`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: Pokemon) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.description)
        val _tmp: String? = __converters.listToString(entity.typeOfPokemon)
        if (_tmp == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmp)
        }
        statement.bindText(5, entity.category)
        statement.bindLong(6, entity.image.toLong())
        statement.bindDouble(7, entity.height)
        statement.bindDouble(8, entity.weight)
        statement.bindLong(9, entity.genderRate.toLong())
        statement.bindLong(10, entity.generationId.toLong())
        statement.bindLong(11, entity.hp.toLong())
        statement.bindLong(12, entity.attack.toLong())
        statement.bindLong(13, entity.defense.toLong())
        statement.bindLong(14, entity.specialAttack.toLong())
        statement.bindLong(15, entity.specialDefense.toLong())
        statement.bindLong(16, entity.speed.toLong())
        val _tmp_1: String = __converters.evolutionListToString(entity.evolutionChain)
        statement.bindText(17, _tmp_1)
        val _tmp_2: String = __converters.pokemonMoveListToString(entity.movesList)
        statement.bindText(18, _tmp_2)
        val _tmp_3: String = __converters.pokemonAbilityListToString(entity.abilitiesList)
        statement.bindText(19, _tmp_3)
      }
    }
    this.__deleteAdapterOfPokemon = object : EntityDeleteOrUpdateAdapter<Pokemon>() {
      protected override fun createQuery(): String = "DELETE FROM `Pokemon` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: Pokemon) {
        statement.bindLong(1, entity.id.toLong())
      }
    }
  }

  public override suspend fun insert(pokemon: Pokemon): Unit = performSuspending(__db, false, true)
      { _connection ->
    __insertAdapterOfPokemon.insert(_connection, pokemon)
  }

  public override suspend fun insertAll(vararg pokemon: Pokemon): Unit = performSuspending(__db,
      false, true) { _connection ->
    __insertAdapterOfPokemon.insert(_connection, pokemon)
  }

  public override suspend fun delete(pokemon: Pokemon): Unit = performSuspending(__db, false, true)
      { _connection ->
    __deleteAdapterOfPokemon.handle(_connection, pokemon)
  }

  public override suspend fun getAll(): List<Pokemon> {
    val _sql: String = "SELECT * FROM pokemon"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfTypeOfPokemon: Int = getColumnIndexOrThrow(_stmt, "types")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfImage: Int = getColumnIndexOrThrow(_stmt, "image")
        val _columnIndexOfHeight: Int = getColumnIndexOrThrow(_stmt, "height")
        val _columnIndexOfWeight: Int = getColumnIndexOrThrow(_stmt, "weight")
        val _columnIndexOfGenderRate: Int = getColumnIndexOrThrow(_stmt, "genderRate")
        val _columnIndexOfGenerationId: Int = getColumnIndexOrThrow(_stmt, "generationId")
        val _columnIndexOfHp: Int = getColumnIndexOrThrow(_stmt, "hp")
        val _columnIndexOfAttack: Int = getColumnIndexOrThrow(_stmt, "attack")
        val _columnIndexOfDefense: Int = getColumnIndexOrThrow(_stmt, "defense")
        val _columnIndexOfSpecialAttack: Int = getColumnIndexOrThrow(_stmt, "specialAttack")
        val _columnIndexOfSpecialDefense: Int = getColumnIndexOrThrow(_stmt, "specialDefense")
        val _columnIndexOfSpeed: Int = getColumnIndexOrThrow(_stmt, "speed")
        val _columnIndexOfEvolutionChain: Int = getColumnIndexOrThrow(_stmt, "evolutions")
        val _columnIndexOfMovesList: Int = getColumnIndexOrThrow(_stmt, "moves")
        val _columnIndexOfAbilitiesList: Int = getColumnIndexOrThrow(_stmt, "abilities")
        val _result: MutableList<Pokemon> = mutableListOf()
        while (_stmt.step()) {
          val _item: Pokemon
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpTypeOfPokemon: List<String>
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfTypeOfPokemon)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfTypeOfPokemon)
          }
          val _tmp_1: List<String>? = __converters.stringToList(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'kotlin.collections.List<kotlin.String>', but it was NULL.")
          } else {
            _tmpTypeOfPokemon = _tmp_1
          }
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpImage: Int
          _tmpImage = _stmt.getLong(_columnIndexOfImage).toInt()
          val _tmpHeight: Double
          _tmpHeight = _stmt.getDouble(_columnIndexOfHeight)
          val _tmpWeight: Double
          _tmpWeight = _stmt.getDouble(_columnIndexOfWeight)
          val _tmpGenderRate: Int
          _tmpGenderRate = _stmt.getLong(_columnIndexOfGenderRate).toInt()
          val _tmpGenerationId: Int
          _tmpGenerationId = _stmt.getLong(_columnIndexOfGenerationId).toInt()
          val _tmpHp: Int
          _tmpHp = _stmt.getLong(_columnIndexOfHp).toInt()
          val _tmpAttack: Int
          _tmpAttack = _stmt.getLong(_columnIndexOfAttack).toInt()
          val _tmpDefense: Int
          _tmpDefense = _stmt.getLong(_columnIndexOfDefense).toInt()
          val _tmpSpecialAttack: Int
          _tmpSpecialAttack = _stmt.getLong(_columnIndexOfSpecialAttack).toInt()
          val _tmpSpecialDefense: Int
          _tmpSpecialDefense = _stmt.getLong(_columnIndexOfSpecialDefense).toInt()
          val _tmpSpeed: Int
          _tmpSpeed = _stmt.getLong(_columnIndexOfSpeed).toInt()
          val _tmpEvolutionChain: List<Evolution>
          val _tmp_2: String
          _tmp_2 = _stmt.getText(_columnIndexOfEvolutionChain)
          _tmpEvolutionChain = __converters.stringToEvolutionList(_tmp_2)
          val _tmpMovesList: List<PokemonMove>
          val _tmp_3: String
          _tmp_3 = _stmt.getText(_columnIndexOfMovesList)
          _tmpMovesList = __converters.stringToPokemonMoveList(_tmp_3)
          val _tmpAbilitiesList: List<PokemonAbility>
          val _tmp_4: String
          _tmp_4 = _stmt.getText(_columnIndexOfAbilitiesList)
          _tmpAbilitiesList = __converters.stringToPokemonAbilityList(_tmp_4)
          _item =
              Pokemon(_tmpId,_tmpName,_tmpDescription,_tmpTypeOfPokemon,_tmpCategory,_tmpImage,_tmpHeight,_tmpWeight,_tmpGenderRate,_tmpGenerationId,_tmpHp,_tmpAttack,_tmpDefense,_tmpSpecialAttack,_tmpSpecialDefense,_tmpSpeed,_tmpEvolutionChain,_tmpMovesList,_tmpAbilitiesList)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllFlow(): Flow<List<Pokemon>> {
    val _sql: String = "SELECT * FROM pokemon"
    return createFlow(__db, false, arrayOf("pokemon")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfTypeOfPokemon: Int = getColumnIndexOrThrow(_stmt, "types")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfImage: Int = getColumnIndexOrThrow(_stmt, "image")
        val _columnIndexOfHeight: Int = getColumnIndexOrThrow(_stmt, "height")
        val _columnIndexOfWeight: Int = getColumnIndexOrThrow(_stmt, "weight")
        val _columnIndexOfGenderRate: Int = getColumnIndexOrThrow(_stmt, "genderRate")
        val _columnIndexOfGenerationId: Int = getColumnIndexOrThrow(_stmt, "generationId")
        val _columnIndexOfHp: Int = getColumnIndexOrThrow(_stmt, "hp")
        val _columnIndexOfAttack: Int = getColumnIndexOrThrow(_stmt, "attack")
        val _columnIndexOfDefense: Int = getColumnIndexOrThrow(_stmt, "defense")
        val _columnIndexOfSpecialAttack: Int = getColumnIndexOrThrow(_stmt, "specialAttack")
        val _columnIndexOfSpecialDefense: Int = getColumnIndexOrThrow(_stmt, "specialDefense")
        val _columnIndexOfSpeed: Int = getColumnIndexOrThrow(_stmt, "speed")
        val _columnIndexOfEvolutionChain: Int = getColumnIndexOrThrow(_stmt, "evolutions")
        val _columnIndexOfMovesList: Int = getColumnIndexOrThrow(_stmt, "moves")
        val _columnIndexOfAbilitiesList: Int = getColumnIndexOrThrow(_stmt, "abilities")
        val _result: MutableList<Pokemon> = mutableListOf()
        while (_stmt.step()) {
          val _item: Pokemon
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpTypeOfPokemon: List<String>
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfTypeOfPokemon)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfTypeOfPokemon)
          }
          val _tmp_1: List<String>? = __converters.stringToList(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'kotlin.collections.List<kotlin.String>', but it was NULL.")
          } else {
            _tmpTypeOfPokemon = _tmp_1
          }
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpImage: Int
          _tmpImage = _stmt.getLong(_columnIndexOfImage).toInt()
          val _tmpHeight: Double
          _tmpHeight = _stmt.getDouble(_columnIndexOfHeight)
          val _tmpWeight: Double
          _tmpWeight = _stmt.getDouble(_columnIndexOfWeight)
          val _tmpGenderRate: Int
          _tmpGenderRate = _stmt.getLong(_columnIndexOfGenderRate).toInt()
          val _tmpGenerationId: Int
          _tmpGenerationId = _stmt.getLong(_columnIndexOfGenerationId).toInt()
          val _tmpHp: Int
          _tmpHp = _stmt.getLong(_columnIndexOfHp).toInt()
          val _tmpAttack: Int
          _tmpAttack = _stmt.getLong(_columnIndexOfAttack).toInt()
          val _tmpDefense: Int
          _tmpDefense = _stmt.getLong(_columnIndexOfDefense).toInt()
          val _tmpSpecialAttack: Int
          _tmpSpecialAttack = _stmt.getLong(_columnIndexOfSpecialAttack).toInt()
          val _tmpSpecialDefense: Int
          _tmpSpecialDefense = _stmt.getLong(_columnIndexOfSpecialDefense).toInt()
          val _tmpSpeed: Int
          _tmpSpeed = _stmt.getLong(_columnIndexOfSpeed).toInt()
          val _tmpEvolutionChain: List<Evolution>
          val _tmp_2: String
          _tmp_2 = _stmt.getText(_columnIndexOfEvolutionChain)
          _tmpEvolutionChain = __converters.stringToEvolutionList(_tmp_2)
          val _tmpMovesList: List<PokemonMove>
          val _tmp_3: String
          _tmp_3 = _stmt.getText(_columnIndexOfMovesList)
          _tmpMovesList = __converters.stringToPokemonMoveList(_tmp_3)
          val _tmpAbilitiesList: List<PokemonAbility>
          val _tmp_4: String
          _tmp_4 = _stmt.getText(_columnIndexOfAbilitiesList)
          _tmpAbilitiesList = __converters.stringToPokemonAbilityList(_tmp_4)
          _item =
              Pokemon(_tmpId,_tmpName,_tmpDescription,_tmpTypeOfPokemon,_tmpCategory,_tmpImage,_tmpHeight,_tmpWeight,_tmpGenderRate,_tmpGenerationId,_tmpHp,_tmpAttack,_tmpDefense,_tmpSpecialAttack,_tmpSpecialDefense,_tmpSpeed,_tmpEvolutionChain,_tmpMovesList,_tmpAbilitiesList)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllByGeneration(generationId: Int): Flow<List<Pokemon>> {
    val _sql: String = "SELECT * FROM pokemon WHERE generationId = ?"
    return createFlow(__db, false, arrayOf("pokemon")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, generationId.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfTypeOfPokemon: Int = getColumnIndexOrThrow(_stmt, "types")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfImage: Int = getColumnIndexOrThrow(_stmt, "image")
        val _columnIndexOfHeight: Int = getColumnIndexOrThrow(_stmt, "height")
        val _columnIndexOfWeight: Int = getColumnIndexOrThrow(_stmt, "weight")
        val _columnIndexOfGenderRate: Int = getColumnIndexOrThrow(_stmt, "genderRate")
        val _columnIndexOfGenerationId: Int = getColumnIndexOrThrow(_stmt, "generationId")
        val _columnIndexOfHp: Int = getColumnIndexOrThrow(_stmt, "hp")
        val _columnIndexOfAttack: Int = getColumnIndexOrThrow(_stmt, "attack")
        val _columnIndexOfDefense: Int = getColumnIndexOrThrow(_stmt, "defense")
        val _columnIndexOfSpecialAttack: Int = getColumnIndexOrThrow(_stmt, "specialAttack")
        val _columnIndexOfSpecialDefense: Int = getColumnIndexOrThrow(_stmt, "specialDefense")
        val _columnIndexOfSpeed: Int = getColumnIndexOrThrow(_stmt, "speed")
        val _columnIndexOfEvolutionChain: Int = getColumnIndexOrThrow(_stmt, "evolutions")
        val _columnIndexOfMovesList: Int = getColumnIndexOrThrow(_stmt, "moves")
        val _columnIndexOfAbilitiesList: Int = getColumnIndexOrThrow(_stmt, "abilities")
        val _result: MutableList<Pokemon> = mutableListOf()
        while (_stmt.step()) {
          val _item: Pokemon
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpTypeOfPokemon: List<String>
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfTypeOfPokemon)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfTypeOfPokemon)
          }
          val _tmp_1: List<String>? = __converters.stringToList(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'kotlin.collections.List<kotlin.String>', but it was NULL.")
          } else {
            _tmpTypeOfPokemon = _tmp_1
          }
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpImage: Int
          _tmpImage = _stmt.getLong(_columnIndexOfImage).toInt()
          val _tmpHeight: Double
          _tmpHeight = _stmt.getDouble(_columnIndexOfHeight)
          val _tmpWeight: Double
          _tmpWeight = _stmt.getDouble(_columnIndexOfWeight)
          val _tmpGenderRate: Int
          _tmpGenderRate = _stmt.getLong(_columnIndexOfGenderRate).toInt()
          val _tmpGenerationId: Int
          _tmpGenerationId = _stmt.getLong(_columnIndexOfGenerationId).toInt()
          val _tmpHp: Int
          _tmpHp = _stmt.getLong(_columnIndexOfHp).toInt()
          val _tmpAttack: Int
          _tmpAttack = _stmt.getLong(_columnIndexOfAttack).toInt()
          val _tmpDefense: Int
          _tmpDefense = _stmt.getLong(_columnIndexOfDefense).toInt()
          val _tmpSpecialAttack: Int
          _tmpSpecialAttack = _stmt.getLong(_columnIndexOfSpecialAttack).toInt()
          val _tmpSpecialDefense: Int
          _tmpSpecialDefense = _stmt.getLong(_columnIndexOfSpecialDefense).toInt()
          val _tmpSpeed: Int
          _tmpSpeed = _stmt.getLong(_columnIndexOfSpeed).toInt()
          val _tmpEvolutionChain: List<Evolution>
          val _tmp_2: String
          _tmp_2 = _stmt.getText(_columnIndexOfEvolutionChain)
          _tmpEvolutionChain = __converters.stringToEvolutionList(_tmp_2)
          val _tmpMovesList: List<PokemonMove>
          val _tmp_3: String
          _tmp_3 = _stmt.getText(_columnIndexOfMovesList)
          _tmpMovesList = __converters.stringToPokemonMoveList(_tmp_3)
          val _tmpAbilitiesList: List<PokemonAbility>
          val _tmp_4: String
          _tmp_4 = _stmt.getText(_columnIndexOfAbilitiesList)
          _tmpAbilitiesList = __converters.stringToPokemonAbilityList(_tmp_4)
          _item =
              Pokemon(_tmpId,_tmpName,_tmpDescription,_tmpTypeOfPokemon,_tmpCategory,_tmpImage,_tmpHeight,_tmpWeight,_tmpGenderRate,_tmpGenerationId,_tmpHp,_tmpAttack,_tmpDefense,_tmpSpecialAttack,_tmpSpecialDefense,_tmpSpeed,_tmpEvolutionChain,_tmpMovesList,_tmpAbilitiesList)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun findById(id: Int): Flow<Pokemon?> {
    val _sql: String = "SELECT * FROM pokemon WHERE id = ? LIMIT 1"
    return createFlow(__db, false, arrayOf("pokemon")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfTypeOfPokemon: Int = getColumnIndexOrThrow(_stmt, "types")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfImage: Int = getColumnIndexOrThrow(_stmt, "image")
        val _columnIndexOfHeight: Int = getColumnIndexOrThrow(_stmt, "height")
        val _columnIndexOfWeight: Int = getColumnIndexOrThrow(_stmt, "weight")
        val _columnIndexOfGenderRate: Int = getColumnIndexOrThrow(_stmt, "genderRate")
        val _columnIndexOfGenerationId: Int = getColumnIndexOrThrow(_stmt, "generationId")
        val _columnIndexOfHp: Int = getColumnIndexOrThrow(_stmt, "hp")
        val _columnIndexOfAttack: Int = getColumnIndexOrThrow(_stmt, "attack")
        val _columnIndexOfDefense: Int = getColumnIndexOrThrow(_stmt, "defense")
        val _columnIndexOfSpecialAttack: Int = getColumnIndexOrThrow(_stmt, "specialAttack")
        val _columnIndexOfSpecialDefense: Int = getColumnIndexOrThrow(_stmt, "specialDefense")
        val _columnIndexOfSpeed: Int = getColumnIndexOrThrow(_stmt, "speed")
        val _columnIndexOfEvolutionChain: Int = getColumnIndexOrThrow(_stmt, "evolutions")
        val _columnIndexOfMovesList: Int = getColumnIndexOrThrow(_stmt, "moves")
        val _columnIndexOfAbilitiesList: Int = getColumnIndexOrThrow(_stmt, "abilities")
        val _result: Pokemon?
        if (_stmt.step()) {
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpTypeOfPokemon: List<String>
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfTypeOfPokemon)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfTypeOfPokemon)
          }
          val _tmp_1: List<String>? = __converters.stringToList(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'kotlin.collections.List<kotlin.String>', but it was NULL.")
          } else {
            _tmpTypeOfPokemon = _tmp_1
          }
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpImage: Int
          _tmpImage = _stmt.getLong(_columnIndexOfImage).toInt()
          val _tmpHeight: Double
          _tmpHeight = _stmt.getDouble(_columnIndexOfHeight)
          val _tmpWeight: Double
          _tmpWeight = _stmt.getDouble(_columnIndexOfWeight)
          val _tmpGenderRate: Int
          _tmpGenderRate = _stmt.getLong(_columnIndexOfGenderRate).toInt()
          val _tmpGenerationId: Int
          _tmpGenerationId = _stmt.getLong(_columnIndexOfGenerationId).toInt()
          val _tmpHp: Int
          _tmpHp = _stmt.getLong(_columnIndexOfHp).toInt()
          val _tmpAttack: Int
          _tmpAttack = _stmt.getLong(_columnIndexOfAttack).toInt()
          val _tmpDefense: Int
          _tmpDefense = _stmt.getLong(_columnIndexOfDefense).toInt()
          val _tmpSpecialAttack: Int
          _tmpSpecialAttack = _stmt.getLong(_columnIndexOfSpecialAttack).toInt()
          val _tmpSpecialDefense: Int
          _tmpSpecialDefense = _stmt.getLong(_columnIndexOfSpecialDefense).toInt()
          val _tmpSpeed: Int
          _tmpSpeed = _stmt.getLong(_columnIndexOfSpeed).toInt()
          val _tmpEvolutionChain: List<Evolution>
          val _tmp_2: String
          _tmp_2 = _stmt.getText(_columnIndexOfEvolutionChain)
          _tmpEvolutionChain = __converters.stringToEvolutionList(_tmp_2)
          val _tmpMovesList: List<PokemonMove>
          val _tmp_3: String
          _tmp_3 = _stmt.getText(_columnIndexOfMovesList)
          _tmpMovesList = __converters.stringToPokemonMoveList(_tmp_3)
          val _tmpAbilitiesList: List<PokemonAbility>
          val _tmp_4: String
          _tmp_4 = _stmt.getText(_columnIndexOfAbilitiesList)
          _tmpAbilitiesList = __converters.stringToPokemonAbilityList(_tmp_4)
          _result =
              Pokemon(_tmpId,_tmpName,_tmpDescription,_tmpTypeOfPokemon,_tmpCategory,_tmpImage,_tmpHeight,_tmpWeight,_tmpGenderRate,_tmpGenerationId,_tmpHp,_tmpAttack,_tmpDefense,_tmpSpecialAttack,_tmpSpecialDefense,_tmpSpeed,_tmpEvolutionChain,_tmpMovesList,_tmpAbilitiesList)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun findByIds(ids: List<Int>): Flow<List<Pokemon>> {
    val _stringBuilder: StringBuilder = StringBuilder()
    _stringBuilder.append("SELECT * FROM pokemon WHERE id IN (")
    val _inputSize: Int = ids.size
    appendPlaceholders(_stringBuilder, _inputSize)
    _stringBuilder.append(")")
    val _sql: String = _stringBuilder.toString()
    return createFlow(__db, false, arrayOf("pokemon")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        for (_item: Int in ids) {
          _stmt.bindLong(_argIndex, _item.toLong())
          _argIndex++
        }
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfTypeOfPokemon: Int = getColumnIndexOrThrow(_stmt, "types")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfImage: Int = getColumnIndexOrThrow(_stmt, "image")
        val _columnIndexOfHeight: Int = getColumnIndexOrThrow(_stmt, "height")
        val _columnIndexOfWeight: Int = getColumnIndexOrThrow(_stmt, "weight")
        val _columnIndexOfGenderRate: Int = getColumnIndexOrThrow(_stmt, "genderRate")
        val _columnIndexOfGenerationId: Int = getColumnIndexOrThrow(_stmt, "generationId")
        val _columnIndexOfHp: Int = getColumnIndexOrThrow(_stmt, "hp")
        val _columnIndexOfAttack: Int = getColumnIndexOrThrow(_stmt, "attack")
        val _columnIndexOfDefense: Int = getColumnIndexOrThrow(_stmt, "defense")
        val _columnIndexOfSpecialAttack: Int = getColumnIndexOrThrow(_stmt, "specialAttack")
        val _columnIndexOfSpecialDefense: Int = getColumnIndexOrThrow(_stmt, "specialDefense")
        val _columnIndexOfSpeed: Int = getColumnIndexOrThrow(_stmt, "speed")
        val _columnIndexOfEvolutionChain: Int = getColumnIndexOrThrow(_stmt, "evolutions")
        val _columnIndexOfMovesList: Int = getColumnIndexOrThrow(_stmt, "moves")
        val _columnIndexOfAbilitiesList: Int = getColumnIndexOrThrow(_stmt, "abilities")
        val _result: MutableList<Pokemon> = mutableListOf()
        while (_stmt.step()) {
          val _item_1: Pokemon
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpTypeOfPokemon: List<String>
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfTypeOfPokemon)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfTypeOfPokemon)
          }
          val _tmp_1: List<String>? = __converters.stringToList(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'kotlin.collections.List<kotlin.String>', but it was NULL.")
          } else {
            _tmpTypeOfPokemon = _tmp_1
          }
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpImage: Int
          _tmpImage = _stmt.getLong(_columnIndexOfImage).toInt()
          val _tmpHeight: Double
          _tmpHeight = _stmt.getDouble(_columnIndexOfHeight)
          val _tmpWeight: Double
          _tmpWeight = _stmt.getDouble(_columnIndexOfWeight)
          val _tmpGenderRate: Int
          _tmpGenderRate = _stmt.getLong(_columnIndexOfGenderRate).toInt()
          val _tmpGenerationId: Int
          _tmpGenerationId = _stmt.getLong(_columnIndexOfGenerationId).toInt()
          val _tmpHp: Int
          _tmpHp = _stmt.getLong(_columnIndexOfHp).toInt()
          val _tmpAttack: Int
          _tmpAttack = _stmt.getLong(_columnIndexOfAttack).toInt()
          val _tmpDefense: Int
          _tmpDefense = _stmt.getLong(_columnIndexOfDefense).toInt()
          val _tmpSpecialAttack: Int
          _tmpSpecialAttack = _stmt.getLong(_columnIndexOfSpecialAttack).toInt()
          val _tmpSpecialDefense: Int
          _tmpSpecialDefense = _stmt.getLong(_columnIndexOfSpecialDefense).toInt()
          val _tmpSpeed: Int
          _tmpSpeed = _stmt.getLong(_columnIndexOfSpeed).toInt()
          val _tmpEvolutionChain: List<Evolution>
          val _tmp_2: String
          _tmp_2 = _stmt.getText(_columnIndexOfEvolutionChain)
          _tmpEvolutionChain = __converters.stringToEvolutionList(_tmp_2)
          val _tmpMovesList: List<PokemonMove>
          val _tmp_3: String
          _tmp_3 = _stmt.getText(_columnIndexOfMovesList)
          _tmpMovesList = __converters.stringToPokemonMoveList(_tmp_3)
          val _tmpAbilitiesList: List<PokemonAbility>
          val _tmp_4: String
          _tmp_4 = _stmt.getText(_columnIndexOfAbilitiesList)
          _tmpAbilitiesList = __converters.stringToPokemonAbilityList(_tmp_4)
          _item_1 =
              Pokemon(_tmpId,_tmpName,_tmpDescription,_tmpTypeOfPokemon,_tmpCategory,_tmpImage,_tmpHeight,_tmpWeight,_tmpGenderRate,_tmpGenerationId,_tmpHp,_tmpAttack,_tmpDefense,_tmpSpecialAttack,_tmpSpecialDefense,_tmpSpeed,_tmpEvolutionChain,_tmpMovesList,_tmpAbilitiesList)
          _result.add(_item_1)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun findByName(name: String): Flow<List<Pokemon>> {
    val _sql: String = "SELECT * FROM pokemon WHERE name LIKE '%' || ? || '%' COLLATE NOCASE"
    return createFlow(__db, false, arrayOf("pokemon")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, name)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfTypeOfPokemon: Int = getColumnIndexOrThrow(_stmt, "types")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfImage: Int = getColumnIndexOrThrow(_stmt, "image")
        val _columnIndexOfHeight: Int = getColumnIndexOrThrow(_stmt, "height")
        val _columnIndexOfWeight: Int = getColumnIndexOrThrow(_stmt, "weight")
        val _columnIndexOfGenderRate: Int = getColumnIndexOrThrow(_stmt, "genderRate")
        val _columnIndexOfGenerationId: Int = getColumnIndexOrThrow(_stmt, "generationId")
        val _columnIndexOfHp: Int = getColumnIndexOrThrow(_stmt, "hp")
        val _columnIndexOfAttack: Int = getColumnIndexOrThrow(_stmt, "attack")
        val _columnIndexOfDefense: Int = getColumnIndexOrThrow(_stmt, "defense")
        val _columnIndexOfSpecialAttack: Int = getColumnIndexOrThrow(_stmt, "specialAttack")
        val _columnIndexOfSpecialDefense: Int = getColumnIndexOrThrow(_stmt, "specialDefense")
        val _columnIndexOfSpeed: Int = getColumnIndexOrThrow(_stmt, "speed")
        val _columnIndexOfEvolutionChain: Int = getColumnIndexOrThrow(_stmt, "evolutions")
        val _columnIndexOfMovesList: Int = getColumnIndexOrThrow(_stmt, "moves")
        val _columnIndexOfAbilitiesList: Int = getColumnIndexOrThrow(_stmt, "abilities")
        val _result: MutableList<Pokemon> = mutableListOf()
        while (_stmt.step()) {
          val _item: Pokemon
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpTypeOfPokemon: List<String>
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfTypeOfPokemon)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfTypeOfPokemon)
          }
          val _tmp_1: List<String>? = __converters.stringToList(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'kotlin.collections.List<kotlin.String>', but it was NULL.")
          } else {
            _tmpTypeOfPokemon = _tmp_1
          }
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpImage: Int
          _tmpImage = _stmt.getLong(_columnIndexOfImage).toInt()
          val _tmpHeight: Double
          _tmpHeight = _stmt.getDouble(_columnIndexOfHeight)
          val _tmpWeight: Double
          _tmpWeight = _stmt.getDouble(_columnIndexOfWeight)
          val _tmpGenderRate: Int
          _tmpGenderRate = _stmt.getLong(_columnIndexOfGenderRate).toInt()
          val _tmpGenerationId: Int
          _tmpGenerationId = _stmt.getLong(_columnIndexOfGenerationId).toInt()
          val _tmpHp: Int
          _tmpHp = _stmt.getLong(_columnIndexOfHp).toInt()
          val _tmpAttack: Int
          _tmpAttack = _stmt.getLong(_columnIndexOfAttack).toInt()
          val _tmpDefense: Int
          _tmpDefense = _stmt.getLong(_columnIndexOfDefense).toInt()
          val _tmpSpecialAttack: Int
          _tmpSpecialAttack = _stmt.getLong(_columnIndexOfSpecialAttack).toInt()
          val _tmpSpecialDefense: Int
          _tmpSpecialDefense = _stmt.getLong(_columnIndexOfSpecialDefense).toInt()
          val _tmpSpeed: Int
          _tmpSpeed = _stmt.getLong(_columnIndexOfSpeed).toInt()
          val _tmpEvolutionChain: List<Evolution>
          val _tmp_2: String
          _tmp_2 = _stmt.getText(_columnIndexOfEvolutionChain)
          _tmpEvolutionChain = __converters.stringToEvolutionList(_tmp_2)
          val _tmpMovesList: List<PokemonMove>
          val _tmp_3: String
          _tmp_3 = _stmt.getText(_columnIndexOfMovesList)
          _tmpMovesList = __converters.stringToPokemonMoveList(_tmp_3)
          val _tmpAbilitiesList: List<PokemonAbility>
          val _tmp_4: String
          _tmp_4 = _stmt.getText(_columnIndexOfAbilitiesList)
          _tmpAbilitiesList = __converters.stringToPokemonAbilityList(_tmp_4)
          _item =
              Pokemon(_tmpId,_tmpName,_tmpDescription,_tmpTypeOfPokemon,_tmpCategory,_tmpImage,_tmpHeight,_tmpWeight,_tmpGenderRate,_tmpGenerationId,_tmpHp,_tmpAttack,_tmpDefense,_tmpSpecialAttack,_tmpSpecialDefense,_tmpSpeed,_tmpEvolutionChain,_tmpMovesList,_tmpAbilitiesList)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteAll() {
    val _sql: String = "DELETE FROM pokemon"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
