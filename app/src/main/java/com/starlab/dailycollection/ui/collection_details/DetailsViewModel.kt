package com.starlab.dailycollection.ui.collection_details

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.starlab.dailycollection.logic.base.repository.DCollectionRepository
import com.starlab.dailycollection.logic.model.DCollection
import kotlin.concurrent.thread

class DetailsViewModel(private val dCollectionRepository: DCollectionRepository) : ViewModel() {

    val additionalList = ArrayList<String>()

    lateinit var dCollectionNew: DCollection

    lateinit var dCollectionOrg: DCollection

    fun updateDCollection() {
        thread { dCollectionRepository.update(dCollectionNew) }
        dCollectionOrg = dCollectionNew.copy()
    }

    fun deleteDCollection(dCollection: DCollection) {
        thread {
            dCollectionRepository.delete(dCollection)
        }
    }


}