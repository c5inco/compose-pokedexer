package des.c5inco.pokedexer.shared.`data`.db

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class PokedexerDatabase_Impl : PokedexerDatabase() {
  private val _pokemonDao: Lazy<PokemonDao> = lazy {
    PokemonDao_Impl(this)
  }

  private val _movesDao: Lazy<MovesDao> = lazy {
    MovesDao_Impl(this)
  }

  private val _itemsDao: Lazy<ItemsDao> = lazy {
    ItemsDao_Impl(this)
  }

  private val _abilitiesDao: Lazy<AbilitiesDao> = lazy {
    AbilitiesDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(1,
        "5950e1cbaabb0b9d159add1ae641c03f", "79417621f6b22290b510ec372d9df77b") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `Pokemon` (`id` INTEGER NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `types` TEXT NOT NULL, `category` TEXT NOT NULL, `image` INTEGER NOT NULL, `height` REAL NOT NULL DEFAULT 0.0, `weight` REAL NOT NULL DEFAULT 0.0, `genderRate` INTEGER NOT NULL DEFAULT -1, `generationId` INTEGER NOT NULL DEFAULT 1, `hp` INTEGER NOT NULL, `attack` INTEGER NOT NULL, `defense` INTEGER NOT NULL, `specialAttack` INTEGER NOT NULL, `specialDefense` INTEGER NOT NULL, `speed` INTEGER NOT NULL, `evolutions` TEXT NOT NULL DEFAULT '', `moves` TEXT NOT NULL DEFAULT '', `abilities` TEXT NOT NULL DEFAULT '', PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `Move` (`id` INTEGER NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `category` TEXT NOT NULL, `type` TEXT NOT NULL, `pp` INTEGER NOT NULL, `power` INTEGER, `accuracy` INTEGER, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `Item` (`id` INTEGER NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `sprite` TEXT NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `Ability` (`id` INTEGER NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '5950e1cbaabb0b9d159add1ae641c03f')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `Pokemon`")
        connection.execSQL("DROP TABLE IF EXISTS `Move`")
        connection.execSQL("DROP TABLE IF EXISTS `Item`")
        connection.execSQL("DROP TABLE IF EXISTS `Ability`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection):
          RoomOpenDelegate.ValidationResult {
        val _columnsPokemon: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsPokemon.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPokemon.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPokemon.put("description", TableInfo.Column("description", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPokemon.put("types", TableInfo.Column("types", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPokemon.put("category", TableInfo.Column("category", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPokemon.put("image", TableInfo.Column("image", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPokemon.put("height", TableInfo.Column("height", "REAL", true, 0, "0.0",
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPokemon.put("weight", TableInfo.Column("weight", "REAL", true, 0, "0.0",
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPokemon.put("genderRate", TableInfo.Column("genderRate", "INTEGER", true, 0, "-1",
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPokemon.put("generationId", TableInfo.Column("generationId", "INTEGER", true, 0,
            "1", TableInfo.CREATED_FROM_ENTITY))
        _columnsPokemon.put("hp", TableInfo.Column("hp", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPokemon.put("attack", TableInfo.Column("attack", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPokemon.put("defense", TableInfo.Column("defense", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPokemon.put("specialAttack", TableInfo.Column("specialAttack", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPokemon.put("specialDefense", TableInfo.Column("specialDefense", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPokemon.put("speed", TableInfo.Column("speed", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPokemon.put("evolutions", TableInfo.Column("evolutions", "TEXT", true, 0, "''",
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPokemon.put("moves", TableInfo.Column("moves", "TEXT", true, 0, "''",
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPokemon.put("abilities", TableInfo.Column("abilities", "TEXT", true, 0, "''",
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysPokemon: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesPokemon: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoPokemon: TableInfo = TableInfo("Pokemon", _columnsPokemon, _foreignKeysPokemon,
            _indicesPokemon)
        val _existingPokemon: TableInfo = read(connection, "Pokemon")
        if (!_infoPokemon.equals(_existingPokemon)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |Pokemon(des.c5inco.pokedexer.shared.model.Pokemon).
              | Expected:
              |""".trimMargin() + _infoPokemon + """
              |
              | Found:
              |""".trimMargin() + _existingPokemon)
        }
        val _columnsMove: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsMove.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMove.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMove.put("description", TableInfo.Column("description", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMove.put("category", TableInfo.Column("category", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMove.put("type", TableInfo.Column("type", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMove.put("pp", TableInfo.Column("pp", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMove.put("power", TableInfo.Column("power", "INTEGER", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMove.put("accuracy", TableInfo.Column("accuracy", "INTEGER", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysMove: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesMove: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoMove: TableInfo = TableInfo("Move", _columnsMove, _foreignKeysMove, _indicesMove)
        val _existingMove: TableInfo = read(connection, "Move")
        if (!_infoMove.equals(_existingMove)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |Move(des.c5inco.pokedexer.shared.model.Move).
              | Expected:
              |""".trimMargin() + _infoMove + """
              |
              | Found:
              |""".trimMargin() + _existingMove)
        }
        val _columnsItem: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsItem.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsItem.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsItem.put("description", TableInfo.Column("description", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsItem.put("sprite", TableInfo.Column("sprite", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysItem: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesItem: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoItem: TableInfo = TableInfo("Item", _columnsItem, _foreignKeysItem, _indicesItem)
        val _existingItem: TableInfo = read(connection, "Item")
        if (!_infoItem.equals(_existingItem)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |Item(des.c5inco.pokedexer.shared.model.Item).
              | Expected:
              |""".trimMargin() + _infoItem + """
              |
              | Found:
              |""".trimMargin() + _existingItem)
        }
        val _columnsAbility: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsAbility.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAbility.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAbility.put("description", TableInfo.Column("description", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysAbility: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesAbility: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoAbility: TableInfo = TableInfo("Ability", _columnsAbility, _foreignKeysAbility,
            _indicesAbility)
        val _existingAbility: TableInfo = read(connection, "Ability")
        if (!_infoAbility.equals(_existingAbility)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |Ability(des.c5inco.pokedexer.shared.model.Ability).
              | Expected:
              |""".trimMargin() + _infoAbility + """
              |
              | Found:
              |""".trimMargin() + _existingAbility)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "Pokemon", "Move", "Item",
        "Ability")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(PokemonDao::class, PokemonDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(MovesDao::class, MovesDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(ItemsDao::class, ItemsDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(AbilitiesDao::class, AbilitiesDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override
      fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>):
      List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun pokemonDao(): PokemonDao = _pokemonDao.value

  public override fun movesDao(): MovesDao = _movesDao.value

  public override fun itemsDao(): ItemsDao = _itemsDao.value

  public override fun abilitiesDao(): AbilitiesDao = _abilitiesDao.value
}
