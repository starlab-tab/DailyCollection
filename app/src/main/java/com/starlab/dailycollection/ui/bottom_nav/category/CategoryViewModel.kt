package com.starlab.dailycollection.ui.bottom_nav.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CategoryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "这是分类界面"
    }
    val text: LiveData<String> = _text
}
