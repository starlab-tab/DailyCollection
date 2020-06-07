package com.starlab.dailycollection

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.core.content.edit
import com.starlab.dailycollection.logic.base.RepositoryProvider
import com.starlab.dailycollection.logic.model.User
import com.starlab.dailycollection.logic.viewmodel.factory.UserViewModelFactory
import kotlin.concurrent.thread

class DailyCollectionApplication : Application(){

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = getSharedPreferences("Encrypt", Context.MODE_PRIVATE)

        if (!sharedPreferences.contains("boolean")) {
            thread {
                RepositoryProvider
                    .getUserRepository(applicationContext)
                    .insert(User())
                applicationContext.getSharedPreferences("Encrypt", Context.MODE_PRIVATE).edit {
                    putInt("boolean", 1)
                }
            }
            Log.d("check0", "putIn")
        }else
            Log.d("check0", "Nor")

        context = applicationContext
    }

}