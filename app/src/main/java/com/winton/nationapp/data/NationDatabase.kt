package com.winton.nationapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Nation::class],
    version = 1

)
abstract class NationDatabase: RoomDatabase() {

    abstract val dao: NationDao
}