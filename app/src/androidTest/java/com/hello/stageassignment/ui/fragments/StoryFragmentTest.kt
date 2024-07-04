package com.hello.stageassignment.ui.fragments

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hello.stageassignment.R
import com.hello.stageassignment.ui.activities.MainActivity
import com.hello.stageassignment.ui.adapters.StoryViewAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/*
@RunWith(AndroidJUnit4::class)
class StoryFragmentTest {

    @Rule
    @JvmField
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun fragmentDisplaysStories() {
        onView(withId(R.id.storyViewRV)).check(matches(isDisplayed()))
    }
}
*/


// Example integration test for StoryFragment using Espresso
@RunWith(AndroidJUnit4::class)
class StoryFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testStoriesDisplayed() {
        onView(withId(R.id.storyViewRV))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testStoryNavigation() {
        onView(withId(R.id.storyViewRV))
            .perform(RecyclerViewActions.actionOnItemAtPosition<StoryViewAdapter.StoryViewHolder>(0, click()))

        onView(withId(R.id.stories))
            .check(matches(isDisplayed()))
    }
}
