package com.starlab.dailycollection.logic.base.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.starlab.dailycollection.logic.base.dao.UserDao
import com.starlab.dailycollection.logic.model.User

class UserRepository private constructor(private val userDao: UserDao) {


    fun  insert(user: User?) = userDao.insert(user)

    fun  update(user: User) = userDao.update(user)

    fun loadUser() = userDao.loadUser()

    fun loadUserById(id: Int) = userDao.loadUserById(id)

    fun findCollectionCount() = userDao.findCollectionCount()

    companion object {

        private var instance: UserRepository? = null

        @Synchronized
        fun getInstance(userDao: UserDao): UserRepository{

            instance?.let {
                return it
            }
            return instance ?: UserRepository(userDao = userDao).apply {
                instance = this
            }

        }


    }


}