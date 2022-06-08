/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality

import androidx.lifecycle.LifecycleOwner
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
//import org.hamcrest.Matchers
//import org.hamcrest.MatcherAssert.assertThat
//import org.junit.Assert
import com.google.common.truth.Truth.assertThat
//import org.junit.Assert.assertThat
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class SleepDatabaseTest {

    private lateinit var sleepDao: SleepDatabaseDao
    private lateinit var db: SleepDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, SleepDatabase::class.java)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
        sleepDao = db.sleepDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNight() {
        val night = SleepNight()
        sleepDao.insert(night)
        val tonight = sleepDao.getTonight()
        assertEquals(tonight?.sleepQuality, -1)
    }

    @Test
    @Throws(Exception::class)
    fun updateNight() {
        val night = SleepNight(1, 100, 100, 1)
        sleepDao.insert(night)
        val newNight = night.copy(nightId = 2)
//        val newNight = night.copy(nightId = 2, startTimeMilli = 100, endTimeMilli = 100, sleepQuality = 1)
        sleepDao.update(newNight)
        val nights = sleepDao.getAllNights().value
        // write a conditional loop to see that "night" isn't empty
        assertThat(nights).contains(newNight)

//        assertEquals(nights!![2].nightId, newNight.nightId)
    //assertThat(nights.javaClass)
    //assertThat("Contains a match", nights.value?.asIterable())
    //assertThat(nights?.contains())
    //assertThat(nights.lastIndex, Matchers.contains() )
    //assertThat(nights.value)
    }
/*
    @Test
    @Throws(Exception::class)
    fun getSleepNights() {
        val night: Long = 1
        sleepDao.get(night)
        val tonight = sleepDao.get(night)
        assertEquals(tonight?.nightId, -1)
    }*/
}