package com.starlab.dailycollection.ui.bottom_nav.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.starlab.dailycollection.logic.base.repository.DCollectionRepository
import com.starlab.dailycollection.logic.model.DCollection
class HomeViewModel(private val dCollectionRepository: DCollectionRepository) : ViewModel() {

    val dCollectionLiveData: LiveData<List<DCollection>> = dCollectionRepository.findAll()

    val dCollectionList = ArrayList<DCollection>()

}
