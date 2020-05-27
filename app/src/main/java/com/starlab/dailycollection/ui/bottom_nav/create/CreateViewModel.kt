package com.starlab.dailycollection.ui.bottom_nav.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "这是新建收藏页面"
    }

    val text : LiveData<String> = _text

}
