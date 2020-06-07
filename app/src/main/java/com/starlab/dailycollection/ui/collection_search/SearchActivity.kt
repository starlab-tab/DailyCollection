package com.starlab.dailycollection.ui.collection_search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.starlab.dailycollection.R
import com.starlab.dailycollection.logic.model.DCollection
import com.starlab.dailycollection.logic.viewmodel.CustomViewModelProvider
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    private val searchViewModel : SearchViewModel by viewModels {
        CustomViewModelProvider.getSearchViewModel(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val layoutManager = GridLayoutManager(this, 2)
        searchRecyclerView.layoutManager = layoutManager
        val adapter = SearchAdapter(this, searchViewModel.resultList)
        searchRecyclerView.adapter = adapter

        searchCollectionEdit.doAfterTextChanged {
            val keyWord = it.toString()
            if (keyWord.isNotEmpty()) {
                searchViewModel.searchCollection(keyWord)
            }else {
                searchRecyclerView.visibility = View.GONE
                searchViewModel.resultList.clear()
                adapter.notifyDataSetChanged()
            }
        }

        searchViewModel.dCollectionSearchLiveData.observe(this, Observer {
            if (it.size > 0) {
                searchRecyclerView.visibility = View.VISIBLE
                searchViewModel.resultList.clear()
                searchViewModel.resultList.addAll(it)
                adapter.notifyDataSetChanged()
            }else {
//                Toast.makeText(this, "没有符合的", Toast.LENGTH_SHORT).show()
            }
        })

    }


}
