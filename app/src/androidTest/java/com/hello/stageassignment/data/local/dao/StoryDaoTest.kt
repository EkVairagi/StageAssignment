package com.hello.stageassignment.data.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hello.stageassignment.data.local.database.StoryDatabase
import com.hello.stageassignment.data.model.StoryEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith



@RunWith(AndroidJUnit4::class)
class StoryDaoTest {

    private lateinit var database: StoryDatabase
    private lateinit var storyDao: StoryDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, StoryDatabase::class.java).build()
        storyDao = database.storyDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertAndGetStories() = runBlocking {
        val story = StoryEntity(storyId = "1", username = "user", userProfile = "url")
        storyDao.insertStories(listOf(story))
        val stories = storyDao.getAllStories().first()
        assertEquals(stories.size, 1)
        assertEquals(stories[0], story)
    }
}
