package com.starlab.dailycollection.logic.base.converters

import android.net.Uri
import android.util.Log
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PostsConverter {

    @TypeConverter
    fun objectToString(list: List<String>?) : String? {
        val gson = Gson()
        return  gson.toJson(list)
    }

    @TypeConverter
    fun stringToObject(value: String?) : List<String>? {

        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
//        if (value?.length!! == 0) return ArrayList<String>()
//        else return Gson().fromJson(value, listType)
    }


}