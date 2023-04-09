package com.zatona.zatona.data.remote

import com.zatona.zatona.models.CategoryResponse
import com.zatona.zatona.models.MealDetailsResponse
import com.zatona.zatona.models.MealResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealsApi {

    @GET("categories.php")
    suspend fun getCategories(): Response<CategoryResponse>

    @GET("filter.php")
    suspend fun getMeals(
        @Query("c") category: String
    ): Response<MealResponse>


    @GET("search.php")
    suspend fun searchForMeal(
        @Query("s") meal: String
    ): Response<MealResponse>


    @GET("lookup.php")
    suspend fun getMealDetails(
        @Query("i") id: String
    ): Response<MealDetailsResponse>



}