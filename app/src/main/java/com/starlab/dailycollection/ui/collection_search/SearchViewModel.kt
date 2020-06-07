package com.starlab.dailycollection.ui.collection_search

import android.util.Log
import androidx.lifecycle.*
import com.starlab.dailycollection.R
import com.starlab.dailycollection.logic.base.repository.DCollectionRepository
import com.starlab.dailycollection.logic.model.DCollection
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class SearchViewModel(private val dCollectionRepository: DCollectionRepository) : ViewModel() {

    val  resultList = ArrayList<DCollection>()

    private val searchLiveData = MutableLiveData<String>()

    val dCollectionSearchLiveData = Transformations.switchMap(searchLiveData) {

        val result = dCollectionRepository.matchByTitle(it)
        Log.d("search0", result.value.toString())
        result
    }

    fun searchCollection(name: String) {
        searchLiveData.value =  "%$name%"
    }
}