package com.zatona.zatona.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zatona.zatona.models.Meal

@Database(entities = [Meal::class], version = 1)
abstract class MealsDatabase : RoomDatabase() {
    abstract fun mealsDao(): MealsDao
}