package com.starlab.dailycollection.logic.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.starlab.dailycollection.logic.base.repository.DCollectionRepository
import com.starlab.dailycollection.ui.collection_search.SearchViewModel

class SearchViewModelFactory(private val dCollectionRepository: DCollectionRepository) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(dCollectionRepository) as T
    }

}