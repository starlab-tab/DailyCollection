package com.starlab.dailycollection

import androidx.room.Room.inMemoryDatabaseBuilder
import com.starlab.dailycollection.logic.base.AppDatabase
import com.starlab.dailycollection.logic.base.dao.DCollectionDao
import com.starlab.dailycollection.logic.base.dao.UserDao
import com.starlab.dailycollection.logic.model.User
import kotlinx.coroutines.coroutineScope
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {



    @Test
    fun listTest() {

        thread {
            Thread.sleep(2000)
            println("threading")
            return@thread
        }
        println("456")
    }

    fun getCurrentTime() = {
        val date = Date()
        val formatted = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        formatted.format(date)

    }
}
