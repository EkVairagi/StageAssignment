package com.hello.stageassignment.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hello.stageassignment.data.local.dao.StoryDao
import com.hello.stageassignment.data.local.database.StoryDatabase
import com.hello.stageassignment.data.model.StoryEntity
import com.hello.stageassignment.repository.StoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class StoryViewModel(application: Application) : AndroidViewModel(application) {
    private val storyDao: StoryDao = StoryDatabase.getDatabase(application).storyDao()
    private val repository = StoryRepository(storyDao)

    val stories: Flow<List<StoryEntity>> = repository.getStories()

    init {
        fetchStories()
    }

    private fun fetchStories() {
        viewModelScope.launch {
            try {
                val storyList = repository.getStoriesFromApi()
                repository.insertStories(storyList)
            } catch (e: Exception) {
                Log.e("Exception: ", e.printStackTrace().toString())
            }
        }
    }
}
