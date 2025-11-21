package com.example.myapplication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FitnessDao {

    @Insert
    suspend fun insertActivity(activity: FitnessEntity)

    @Query("SELECT * FROM activities ORDER BY date DESC")
    suspend fun getAllActivities(): List<FitnessEntity>

    @Query("SELECT * FROM activities WHERE date = :selectedDate")
    suspend fun getActivitiesByDate(selectedDate: String): List<FitnessEntity>

    @Query("SELECT SUM(duration) FROM activities WHERE date = :selectedDate")
    suspend fun getTotalDuration(selectedDate: String): Int?
}
