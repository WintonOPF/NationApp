package com.winton.nationapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNation(nation: Nation)

    @Delete
    suspend fun deleteNation(nation: Nation)

    @Query("SELECT * FROM nation WHERE id = :id")
    suspend fun getNationById(id: Int): Nation?

    // real database changes
    @Query("SELECT * FROM nation")
    fun getNations(): Flow<List<Nation>>
}