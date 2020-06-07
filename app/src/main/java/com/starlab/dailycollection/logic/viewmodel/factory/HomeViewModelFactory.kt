package com.starlab.dailycollection.logic.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.starlab.dailycollection.logic.base.repository.DCollectionRepository
import com.starlab.dailycollection.ui.bottom_nav.home.HomeViewModel

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val dCollectionRepository: DCollectionRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(dCollectionRepository) as T
    }
}