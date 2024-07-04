package com.hello.stageassignment.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hello.stageassignment.data.local.dao.StoryDao
import com.hello.stageassignment.data.local.database.StoryDatabase
import com.hello.stageassignment.data.model.StoryEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StoryRepositoryTest {

    private lateinit var repository: StoryRepository
    private lateinit var storyDao: StoryDao
    private lateinit var database: StoryDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, StoryDatabase::class.java).build()
        storyDao = database.storyDao()
        repository = StoryRepository(storyDao)
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertAndGetStoriesFromApi() = runBlocking {
        val stories = repository.getStoriesFromApi()
        repository.insertStories(stories)
        val retrievedStories = repository.getStories().first()
        assertEquals(retrievedStories.size, stories.size)
    }
}
