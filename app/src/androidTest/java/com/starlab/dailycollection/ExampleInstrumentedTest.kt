package com.starlab.dailycollection

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.starlab.dailycollection.logic.base.AppDatabase
import com.starlab.dailycollection.logic.base.dao.DCollectionDao
import com.starlab.dailycollection.logic.base.dao.UserDao
import com.starlab.dailycollection.logic.model.DCollection
import com.starlab.dailycollection.logic.model.User
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private lateinit var userDao: UserDao
    private lateinit var dCollectionDao: DCollectionDao
    private lateinit var db: AppDatabase

    @Before
    fun  createDb() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java).build()

    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun roomTest() {


    }

    fun getCurrentTime(): String {
        val date = Date()
        val formatted = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return formatted.format(date)
    }

}
