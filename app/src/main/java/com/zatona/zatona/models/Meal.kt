package com.zatona.zatona.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zatona.zatona.utils.Constants.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)

data class Meal(
    @PrimaryKey(autoGenerate = false)
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    var isFavorite: Boolean = false
)