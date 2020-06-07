package com.starlab.dailycollection.logic.base.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.starlab.dailycollection.logic.model.DCollection

@Dao
interface DCollectionDao {

    @Insert
    fun insert(dCollection: DCollection): Long

    @Update
    fun update(dCollection: DCollection)

    @Delete
    fun delete(dCollection: DCollection)

    @Query("delete from dcollection where id = :id")
    fun deleteById(id: Int)

    @Query("delete from dcollection where dcollection_title = :name")
    fun deleteByName(name: String)

    @Query("delete from dcollection where dcollection_title = :title")
    fun deleteByTitle(title: String)

    @Query("select * from dcollection")
    fun findAll(): LiveData<List<DCollection>>

    @Query("select * from dcollection where dcollection_title = :title")
    fun findByTitle(title: String): LiveData<List<DCollection>>

    @Query("select * from dcollection where dcollection_title like :title")
    fun matchByTitle(title: String): LiveData<List<DCollection>>

//    @Query("select it.id, it.dcollection_cover, it.dcollection_title from dcollection as it")
//    fun findAllForView(): List<Any>

}