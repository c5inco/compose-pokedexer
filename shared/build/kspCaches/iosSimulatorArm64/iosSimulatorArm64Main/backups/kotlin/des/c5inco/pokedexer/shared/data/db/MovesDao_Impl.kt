package des.c5inco.pokedexer.shared.`data`.db

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.appendPlaceholders
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import des.c5inco.pokedexer.shared.model.Move
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
public class MovesDao_Impl(
  __db: RoomDatabase,
) : MovesDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfMove: EntityInsertAdapter<Move>

  private val __deleteAdapterOfMove: EntityDeleteOrUpdateAdapter<Move>
  init {
    this.__db = __db
    this.__insertAdapterOfMove = object : EntityInsertAdapter<Move>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `Move` (`id`,`name`,`description`,`category`,`type`,`pp`,`power`,`accuracy`) VALUES (?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: Move) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.description)
        statement.bindText(4, entity.category)
        statement.bindText(5, entity.type)
        statement.bindLong(6, entity.pp.toLong())
        val _tmpPower: Int? = entity.power
        if (_tmpPower == null) {
          statement.bindNull(7)
        } else {
          statement.bindLong(7, _tmpPower.toLong())
        }
        val _tmpAccuracy: Int? = entity.accuracy
        if (_tmpAccuracy == null) {
          statement.bindNull(8)
        } else {
          statement.bindLong(8, _tmpAccuracy.toLong())
        }
      }
    }
    this.__deleteAdapterOfMove = object : EntityDeleteOrUpdateAdapter<Move>() {
      protected override fun createQuery(): String = "DELETE FROM `Move` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: Move) {
        statement.bindLong(1, entity.id.toLong())
      }
    }
  }

  public override suspend fun insert(move: Move): Unit = performSuspending(__db, false, true) {
      _connection ->
    __insertAdapterOfMove.insert(_connection, move)
  }

  public override suspend fun insertAll(vararg move: Move): Unit = performSuspending(__db, false,
      true) { _connection ->
    __insertAdapterOfMove.insert(_connection, move)
  }

  public override suspend fun delete(move: Move): Unit = performSuspending(__db, false, true) {
      _connection ->
    __deleteAdapterOfMove.handle(_connection, move)
  }

  public override fun getAll(): Flow<List<Move>> {
    val _sql: String = "SELECT * FROM move"
    return createFlow(__db, false, arrayOf("move")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfPp: Int = getColumnIndexOrThrow(_stmt, "pp")
        val _columnIndexOfPower: Int = getColumnIndexOrThrow(_stmt, "power")
        val _columnIndexOfAccuracy: Int = getColumnIndexOrThrow(_stmt, "accuracy")
        val _result: MutableList<Move> = mutableListOf()
        while (_stmt.step()) {
          val _item: Move
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpType: String
          _tmpType = _stmt.getText(_columnIndexOfType)
          val _tmpPp: Int
          _tmpPp = _stmt.getLong(_columnIndexOfPp).toInt()
          val _tmpPower: Int?
          if (_stmt.isNull(_columnIndexOfPower)) {
            _tmpPower = null
          } else {
            _tmpPower = _stmt.getLong(_columnIndexOfPower).toInt()
          }
          val _tmpAccuracy: Int?
          if (_stmt.isNull(_columnIndexOfAccuracy)) {
            _tmpAccuracy = null
          } else {
            _tmpAccuracy = _stmt.getLong(_columnIndexOfAccuracy).toInt()
          }
          _item =
              Move(_tmpId,_tmpName,_tmpDescription,_tmpCategory,_tmpType,_tmpPp,_tmpPower,_tmpAccuracy)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun findById(id: Int): Flow<Move?> {
    val _sql: String = "SELECT * FROM move WHERE id = ? LIMIT 1"
    return createFlow(__db, false, arrayOf("move")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfPp: Int = getColumnIndexOrThrow(_stmt, "pp")
        val _columnIndexOfPower: Int = getColumnIndexOrThrow(_stmt, "power")
        val _columnIndexOfAccuracy: Int = getColumnIndexOrThrow(_stmt, "accuracy")
        val _result: Move?
        if (_stmt.step()) {
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpType: String
          _tmpType = _stmt.getText(_columnIndexOfType)
          val _tmpPp: Int
          _tmpPp = _stmt.getLong(_columnIndexOfPp).toInt()
          val _tmpPower: Int?
          if (_stmt.isNull(_columnIndexOfPower)) {
            _tmpPower = null
          } else {
            _tmpPower = _stmt.getLong(_columnIndexOfPower).toInt()
          }
          val _tmpAccuracy: Int?
          if (_stmt.isNull(_columnIndexOfAccuracy)) {
            _tmpAccuracy = null
          } else {
            _tmpAccuracy = _stmt.getLong(_columnIndexOfAccuracy).toInt()
          }
          _result =
              Move(_tmpId,_tmpName,_tmpDescription,_tmpCategory,_tmpType,_tmpPp,_tmpPower,_tmpAccuracy)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun findByIds(ids: List<Int>): List<Move> {
    val _stringBuilder: StringBuilder = StringBuilder()
    _stringBuilder.append("SELECT * FROM move WHERE id IN (")
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
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfPp: Int = getColumnIndexOrThrow(_stmt, "pp")
        val _columnIndexOfPower: Int = getColumnIndexOrThrow(_stmt, "power")
        val _columnIndexOfAccuracy: Int = getColumnIndexOrThrow(_stmt, "accuracy")
        val _result: MutableList<Move> = mutableListOf()
        while (_stmt.step()) {
          val _item_1: Move
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpType: String
          _tmpType = _stmt.getText(_columnIndexOfType)
          val _tmpPp: Int
          _tmpPp = _stmt.getLong(_columnIndexOfPp).toInt()
          val _tmpPower: Int?
          if (_stmt.isNull(_columnIndexOfPower)) {
            _tmpPower = null
          } else {
            _tmpPower = _stmt.getLong(_columnIndexOfPower).toInt()
          }
          val _tmpAccuracy: Int?
          if (_stmt.isNull(_columnIndexOfAccuracy)) {
            _tmpAccuracy = null
          } else {
            _tmpAccuracy = _stmt.getLong(_columnIndexOfAccuracy).toInt()
          }
          _item_1 =
              Move(_tmpId,_tmpName,_tmpDescription,_tmpCategory,_tmpType,_tmpPp,_tmpPower,_tmpAccuracy)
          _result.add(_item_1)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun findByName(name: String): Flow<List<Move>> {
    val _sql: String = "SELECT * FROM move WHERE name LIKE '%' || ? || '%' COLLATE NOCASE"
    return createFlow(__db, false, arrayOf("move")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, name)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfPp: Int = getColumnIndexOrThrow(_stmt, "pp")
        val _columnIndexOfPower: Int = getColumnIndexOrThrow(_stmt, "power")
        val _columnIndexOfAccuracy: Int = getColumnIndexOrThrow(_stmt, "accuracy")
        val _result: MutableList<Move> = mutableListOf()
        while (_stmt.step()) {
          val _item: Move
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpType: String
          _tmpType = _stmt.getText(_columnIndexOfType)
          val _tmpPp: Int
          _tmpPp = _stmt.getLong(_columnIndexOfPp).toInt()
          val _tmpPower: Int?
          if (_stmt.isNull(_columnIndexOfPower)) {
            _tmpPower = null
          } else {
            _tmpPower = _stmt.getLong(_columnIndexOfPower).toInt()
          }
          val _tmpAccuracy: Int?
          if (_stmt.isNull(_columnIndexOfAccuracy)) {
            _tmpAccuracy = null
          } else {
            _tmpAccuracy = _stmt.getLong(_columnIndexOfAccuracy).toInt()
          }
          _item =
              Move(_tmpId,_tmpName,_tmpDescription,_tmpCategory,_tmpType,_tmpPp,_tmpPower,_tmpAccuracy)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteAll() {
    val _sql: String = "DELETE FROM move"
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
