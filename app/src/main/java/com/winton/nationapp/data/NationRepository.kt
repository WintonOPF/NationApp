package com.winton.nationapp.data

import kotlinx.coroutines.flow.Flow

interface NationRepository {

    suspend fun insertNation(nation: Nation)

    suspend fun deleteNation(nation: Nation)

    suspend fun getNationById(Id: Int): Nation?

    fun getNations(): Flow<List<Nation>>
}