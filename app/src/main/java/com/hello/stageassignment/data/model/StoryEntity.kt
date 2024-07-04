package com.hello.stageassignment.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "stories")
@Parcelize
data class StoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val storyId: String,
    val username: String = "",
    val userProfile: String = "",
    val imageUrls: List<String> = emptyList(),
    val storyTimes: List<String> = emptyList()
): Parcelable


