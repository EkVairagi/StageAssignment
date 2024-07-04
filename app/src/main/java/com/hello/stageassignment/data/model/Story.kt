package com.hello.stageassignment.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Story(
    val id: String = "",
    val username: String = "",
    val userProfile: String = "",
    val imageUrls: List<String> = emptyList(),
    val storyTimes: List<String> = emptyList()
) : Parcelable
