package com.starlab.dailycollection.logic.base.converters

import android.net.Uri
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CoverConverter {

    @TypeConverter
    fun stringToUri(value: String?) : Uri {

        val type = object : TypeToken<Uri>() {}.type
        return  Gson().fromJson(value, type)

    }

    @TypeConverter
    fun uriToString(uri: Uri?) : String? {
        val gson = Gson()
        return  gson.toJson(uri)
    }

}