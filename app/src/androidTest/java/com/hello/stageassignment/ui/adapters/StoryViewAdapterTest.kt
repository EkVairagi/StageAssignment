package com.hello.stageassignment.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bumptech.glide.Glide
import com.hello.stageassignment.R
import com.hello.stageassignment.databinding.StoryItemLayoutBinding
import com.hello.stageassignment.data.model.StoryEntity
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class StoryViewAdapterTest {

    @Test
    fun bindViewHolder_displaysStoryDetails() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val recyclerView = RecyclerView(context).apply {
            layoutManager = LinearLayoutManager(context)
        }
        val adapter = StoryViewAdapter { }
        recyclerView.adapter = adapter

        val story = StoryEntity(storyId = "1", username = "user", userProfile = "url")
        adapter.submitList(listOf(story))

        adapter.onBindViewHolder(adapter.createViewHolder(FrameLayout(context), 0), 0)

        onView(withId(R.id.username)).check(matches(withText("user")))
        // Add more assertions as needed
    }
}
