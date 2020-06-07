package com.starlab.dailycollection.ui.bottom_nav.create

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.starlab.dailycollection.DailyCollectionApplication
import com.starlab.dailycollection.logic.base.repository.DCollectionRepository
import com.starlab.dailycollection.logic.model.DCollection
import com.starlab.dailycollection.ui.collection_details.DetailsActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.concurrent.thread

class CreateViewModel(private val dCollectionRepository: DCollectionRepository) : ViewModel() {

    val dCollection = DCollection()

    val additionalList = ArrayList<String>()

    fun create(context: Context) {

        viewModelScope.launch(Dispatchers.IO){
            val id = async {
                dCollectionRepository.insert(dCollection)
            }
            dCollection.id = id.await()
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("dcollection_parcelize", dCollection)
            context.startActivity(intent)
        }
    }
}
