package com.starlab.dailycollection.logic.base.repository

import com.starlab.dailycollection.logic.base.dao.DCollectionDao
import com.starlab.dailycollection.logic.model.DCollection


class DCollectionRepository private constructor(private val dCollectionDao: DCollectionDao) {

    fun insert(dCollection: DCollection) = dCollectionDao.insert(dCollection)

    fun update(dCollection: DCollection) = dCollectionDao.update(dCollection)

    fun deleteById(dCollectionId: Int) = dCollectionDao.deleteById(dCollectionId)

    fun deleteByName(name: String) = dCollectionDao.deleteByName(name)

    fun findByTitle(title: String) = dCollectionDao.findByTitle(title)

    fun delete(dCollection: DCollection) = dCollectionDao.delete(dCollection)

    fun findAll() = dCollectionDao.findAll()

    fun matchByTitle(title: String) = dCollectionDao.matchByTitle(title)

    companion object {

        private var instance: DCollectionRepository? = null

        @Synchronized
        fun getInstance(dCollectionDao: DCollectionDao): DCollectionRepository {

            instance?.let {
                return it
            }
            return instance ?: DCollectionRepository(dCollectionDao = dCollectionDao).apply {
                instance = this
            }
        }
    }
}