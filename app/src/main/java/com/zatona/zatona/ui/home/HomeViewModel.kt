package com.zatona.zatona.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zatona.zatona.data.repository.Repository
import com.zatona.zatona.models.Category
import com.zatona.zatona.models.Meal
import com.zatona.zatona.models.MealDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var favorites: LiveData<List<Meal>>

    init {
        favorites = repository.getFavourite()
        getCategories()
    }

    private val _searchData: MutableLiveData<List<Meal>> = MutableLiveData()
    val searchData: LiveData<List<Meal>> = _searchData

    private val _mealDetails: MutableLiveData<MealDetails?> = MutableLiveData()
    val mealDetails: LiveData<MealDetails?> = _mealDetails


    private val _catData: MutableLiveData<List<Category>> = MutableLiveData()
    val catData: LiveData<List<Category>> = _catData


    private val _mealData: MutableLiveData<List<Meal>> = MutableLiveData()
    val mealData: LiveData<List<Meal>> = _mealData

    val mealId: MutableLiveData<String> = MutableLiveData()


    private fun getCategories() = viewModelScope.launch {
        try {
            val response = repository.getCategories()
            if (response.isSuccessful) {
                _catData.postValue(response.body()?.categories)
                Log.e("Great request", "getData: Great")
            } else Log.e("Failed request", "getData: Failed")
        } catch (ex: Exception) {
            Log.e("TAG", ex.message.toString())
        }
    }

    fun getMeals(category: String) = viewModelScope.launch {
        try {
            val response = repository.getMeals(category)
            if (response.isSuccessful) {
                _mealData.postValue(response.body()?.meals)
                Log.e("Great request", "getData: Great")
            } else Log.e("Failed request", response.errorBody().toString())
        } catch (ex: Exception) {
            Log.e("TAG", ex.message.toString())
        }


    }

    fun search(query: String) = viewModelScope.launch {
        if (query.isNotEmpty()) {
            val response = repository.searchForMeal(query.trim())
            _searchData.postValue(response.body()?.meals)
        } else {
            _searchData.postValue(emptyList())
        }
    }

    fun getMealDetails(id: String) = viewModelScope.launch {
        val response = repository.getMealDetails(id)
        _mealDetails.postValue(response)

    }

    fun addFavourite(meal: Meal) = viewModelScope.launch { repository.addFavourite(meal) }

    fun removeFavourite(meal: Meal) = viewModelScope.launch { repository.removeFavourite(meal) }

    fun clearSearchResults() {
        _searchData.value = emptyList()
    }


    override fun onCleared() {
        super.onCleared()
        clearSearchResults()
    }


    fun saveUserName(value: String) {
        viewModelScope.launch {
            repository.saveUserName("userName", value)
        }
    }

    fun getUserName(): String? = runBlocking {
        repository.getUserName("userName")
    }


}