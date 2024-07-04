package com.hello.stageassignment.data.remote

import com.hello.stageassignment.data.model.Story
import retrofit2.http.GET


interface ApiService {
    @GET("db.json")
    suspend fun getStories(): List<Story>
}
