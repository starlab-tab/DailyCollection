package com.starlab.dailycollection.logic.base

import android.content.Context
import com.starlab.dailycollection.logic.base.repository.DCollectionRepository
import com.starlab.dailycollection.logic.base.repository.UserRepository

object RepositoryProvider {

    fun getUserRepository(context: Context): UserRepository {

        return UserRepository.getInstance(AppDatabase.getDatabase(context).userDao())
    }

    fun getDCollectioonRepository(context: Context): DCollectionRepository {

        return DCollectionRepository
            .getInstance(AppDatabase
                .getDatabase(context)
                .dCollectionDao())

    }


}