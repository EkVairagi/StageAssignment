package com.hello.stageassignment.ui.fragments

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.hello.stageassignment.R
import com.hello.stageassignment.databinding.FragmentSecondBinding
import com.hello.stageassignment.data.model.StoryEntity
import com.hello.stageassignment.story.StoriesProgressView
import com.hello.stageassignment.ui.activities.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ViewStoryFragmentTest {

    @Rule
    @JvmField
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun fragmentDisplaysStories() {
        onView(withId(R.id.stories)).check(matches(isDisplayed()))
    }

    @Test
    fun fragmentLoadsStoryOnNext() {
        onView(withId(R.id.stories)).perform(click())
        onView(withId(R.id.image)).check(matches(isDisplayed()))
    }
}
