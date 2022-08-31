package com.winton.nationapp.data

import kotlinx.coroutines.flow.Flow

class NationRepositoryImpl(
    private val dao: NationDao
): NationRepository {

    override suspend fun insertNation(nation: Nation) {
        dao.insertNation(nation)
    }

    override suspend fun deleteNation(nation: Nation) {
        dao.deleteNation(nation)
    }

    override suspend fun getNationById(id: Int): Nation? {
        return dao.getNationById(id)
    }

    override fun getNations(): Flow<List<Nation>> {
        return dao.getNations()
    }
}