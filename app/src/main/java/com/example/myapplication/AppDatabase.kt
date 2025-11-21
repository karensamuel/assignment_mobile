package com.example.myapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FitnessEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun activityDao(): FitnessDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "activity_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
