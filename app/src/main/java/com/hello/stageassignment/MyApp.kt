package com.hello.stageassignment

import android.app.Application
import com.hello.stageassignment.data.local.database.StoryDatabase

class YourApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        StoryDatabase.getDatabase(this)
    }
}
