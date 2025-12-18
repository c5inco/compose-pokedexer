package des.c5inco.pokedexer.shared.`data`.db

import androidx.room.RoomDatabaseConstructor

public actual object PokedexerDatabaseConstructor : RoomDatabaseConstructor<PokedexerDatabase> {
  actual override fun initialize(): PokedexerDatabase =
      des.c5inco.pokedexer.shared.`data`.db.PokedexerDatabase_Impl()
}
