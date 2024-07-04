package com.hello.stageassignment.repository

import com.hello.stageassignment.data.local.dao.StoryDao
import com.hello.stageassignment.data.remote.RetrofitInstance
import com.hello.stageassignment.data.model.StoryEntity
import kotlinx.coroutines.flow.Flow


class StoryRepository(private val storyDao: StoryDao) {
    fun getStories(): Flow<List<StoryEntity>> {
        return storyDao.getAllStories()
    }

    suspend fun insertStories(stories: List<StoryEntity>) {
        storyDao.insertStories(stories)
    }

    suspend fun getStoriesFromApi(): List<StoryEntity> {
        val stories = RetrofitInstance.api.getStories()
        return stories.map {
            StoryEntity(
                storyId = it.id,
                username = it.username,
                userProfile = it.userProfile,
                imageUrls = it.imageUrls,
                storyTimes = it.storyTimes
            )
        }
    }
}