package com.starlab.dailycollection.logic.base.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.starlab.dailycollection.logic.model.User

@Dao
interface UserDao {

    @Insert
    fun insert(user: User?): Long

    @Update
    fun update(user: User)

    @Query("select * from user where user_code_name = :codeName")
    fun findUserByCodeName(codeName: String): User

    @Query("select * from user")
    fun loadUser(): LiveData<User>

    @Query("select * from user where id = :id")
    fun loadUserById(id: Int): LiveData<User>

    @Query("select count(*) from dcollection")
    fun findCollectionCount(): LiveData<Int>
}