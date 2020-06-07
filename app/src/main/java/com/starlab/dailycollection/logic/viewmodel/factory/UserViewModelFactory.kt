package com.starlab.dailycollection.logic.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.starlab.dailycollection.logic.base.repository.UserRepository
import com.starlab.dailycollection.logic.viewmodel.UserViewModel

class UserViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(userRepository) as T
    }

}