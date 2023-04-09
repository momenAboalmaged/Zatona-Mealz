package com.zatona.zatona.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.zatona.zatona.models.Meal
import com.zatona.zatona.utils.Constants

@Dao
interface MealsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(meal: Meal): Long

    @Query("SELECT * FROM meals_table ")
    fun getFavMeals(): LiveData<List<Meal>>


    @Delete
    suspend fun delete(meal: Meal)

    @Update
    suspend fun update(meal: Meal)


}