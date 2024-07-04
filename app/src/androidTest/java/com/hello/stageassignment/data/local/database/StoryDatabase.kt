package com.hello.stageassignment.data.local.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.hello.stageassignment.data.local.converters.Converters
import com.hello.stageassignment.data.local.dao.StoryDao
import com.hello.stageassignment.data.model.StoryEntity

@Database(entities = [StoryEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class StoryDatabase : RoomDatabase() {
    abstract fun storyDao(): StoryDao

    companion object {
        @Volatile
        private var INSTANCE: StoryDatabase? = null

        fun getDatabase(context: Context): StoryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StoryDatabase::class.java,
                    "story_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
