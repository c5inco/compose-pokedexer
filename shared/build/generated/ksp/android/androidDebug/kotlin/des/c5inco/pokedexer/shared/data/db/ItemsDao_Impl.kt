package des.c5inco.pokedexer.shared.`data`.db

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.appendPlaceholders
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import des.c5inco.pokedexer.shared.model.Item
import javax.`annotation`.processing.Generated
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

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class ItemsDao_Impl(
  __db: RoomDatabase,
) : ItemsDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfItem: EntityInsertAdapter<Item>
  init {
    this.__db = __db
    this.__insertAdapterOfItem = object : EntityInsertAdapter<Item>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `Item` (`id`,`name`,`description`,`sprite`) VALUES (?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: Item) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.description)
        statement.bindText(4, entity.sprite)
      }
    }
  }

  public override suspend fun insert(item: Item): Unit = performSuspending(__db, false, true) {
      _connection ->
    __insertAdapterOfItem.insert(_connection, item)
  }

  public override suspend fun insertAll(vararg item: Item): Unit = performSuspending(__db, false,
      true) { _connection ->
    __insertAdapterOfItem.insert(_connection, item)
  }

  public override fun getAll(): Flow<List<Item>> {
    val _sql: String = "SELECT * FROM item"
    return createFlow(__db, false, arrayOf("item")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfSprite: Int = getColumnIndexOrThrow(_stmt, "sprite")
        val _result: MutableList<Item> = mutableListOf()
        while (_stmt.step()) {
          val _item: Item
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpSprite: String
          _tmpSprite = _stmt.getText(_columnIndexOfSprite)
          _item = Item(_tmpId,_tmpName,_tmpDescription,_tmpSprite)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun findById(id: Int): Flow<Item?> {
    val _sql: String = "SELECT * FROM item WHERE id = ? LIMIT 1"
    return createFlow(__db, false, arrayOf("item")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfSprite: Int = getColumnIndexOrThrow(_stmt, "sprite")
        val _result: Item?
        if (_stmt.step()) {
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpSprite: String
          _tmpSprite = _stmt.getText(_columnIndexOfSprite)
          _result = Item(_tmpId,_tmpName,_tmpDescription,_tmpSprite)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun findByIds(ids: List<Int>): List<Item> {
    val _stringBuilder: StringBuilder = StringBuilder()
    _stringBuilder.append("SELECT * FROM item WHERE id IN (")
    val _inputSize: Int = ids.size
    appendPlaceholders(_stringBuilder, _inputSize)
    _stringBuilder.append(")")
    val _sql: String = _stringBuilder.toString()
    return performSuspending(__db, true, false) { _connection ->
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
        val _columnIndexOfSprite: Int = getColumnIndexOrThrow(_stmt, "sprite")
        val _result: MutableList<Item> = mutableListOf()
        while (_stmt.step()) {
          val _item_1: Item
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpSprite: String
          _tmpSprite = _stmt.getText(_columnIndexOfSprite)
          _item_1 = Item(_tmpId,_tmpName,_tmpDescription,_tmpSprite)
          _result.add(_item_1)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun findByName(name: String): Flow<List<Item>> {
    val _sql: String = "SELECT * FROM item WHERE name LIKE '%' || ? || '%' COLLATE NOCASE"
    return createFlow(__db, false, arrayOf("item")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, name)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfSprite: Int = getColumnIndexOrThrow(_stmt, "sprite")
        val _result: MutableList<Item> = mutableListOf()
        while (_stmt.step()) {
          val _item: Item
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpSprite: String
          _tmpSprite = _stmt.getText(_columnIndexOfSprite)
          _item = Item(_tmpId,_tmpName,_tmpDescription,_tmpSprite)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteAll() {
    val _sql: String = "DELETE FROM item"
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
