package com.hello.stageassignment.ui.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hello.stageassignment.data.local.dao.StoryDao
import com.hello.stageassignment.data.local.database.StoryDatabase
import com.hello.stageassignment.data.model.StoryEntity
import com.hello.stageassignment.repository.StoryRepository
import com.hello.stageassignment.repository.StoryRepositoryTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StoryViewModelTest {

    private lateinit var viewModel: StoryViewModel
    private lateinit var repository: StoryRepository
    private lateinit var storyDao: StoryDao
    private lateinit var database: StoryDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, StoryDatabase::class.java).build()
        storyDao = database.storyDao()
        repository = StoryRepository(storyDao)
        viewModel = StoryViewModel(ApplicationProvider.getApplicationContext())
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun fetchStories_updatesLiveData() = runBlocking {
        val story = StoryEntity(storyId = "1", username = "user", userProfile = "url")
        storyDao.insertStories(listOf(story))

        viewModel.stories.onEach { stories ->
            assertEquals(stories.size, 1)
            assertEquals(stories[0], story)
        }.launchIn(viewModel.viewModelScope)
    }
}
