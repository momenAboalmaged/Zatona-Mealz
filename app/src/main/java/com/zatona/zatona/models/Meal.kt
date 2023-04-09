package com.zatona.zatona.models
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zatona.zatona.utils.Constants.Companion.TABLE_NAME
import kotlinx.parcelize.Parcelize

@Entity(tableName = TABLE_NAME)

data class Meal(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    var isFavorite: Boolean = false
)