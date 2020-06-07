package com.starlab.dailycollection.logic.viewmodel

import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.starlab.dailycollection.DailyCollectionApplication
import com.starlab.dailycollection.logic.base.repository.UserRepository
import com.starlab.dailycollection.logic.model.User
import kotlin.concurrent.thread

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    val userLiveData: LiveData<User> = userRepository.loadUser()

    lateinit var userCopy: User

    lateinit var userNew: User

    val dCountLiveData: LiveData<Int> = userRepository.findCollectionCount()

    fun updateUser() {

        thread {
            userRepository.update(userNew)
        }
        userCopy = userNew.copy()
    }

//    val codeNameLiveData = MutableLiveData<String>()
//
//    val wordLiveData = MutableLiveData<String>()

}