package com.benny.kitchinmobile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benny.kitchinmobile.data.RecipeDao
import com.benny.kitchinmobile.domain.Recipe
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecipeViewModel(private val recipeDao: RecipeDao) : ViewModel() {

    val recipes: StateFlow<List<Recipe>> = recipeDao.getAllRecipes()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addRecipe(title: String, prepTime: String, ingredients: String, instructions: String) {
        viewModelScope.launch {
            val newRecipe = Recipe(
                title = title,
                prepTime = prepTime,
                ingredients = ingredients.split(",").map { it.trim() },
                instructions = instructions
            )
            recipeDao.insertRecipe(newRecipe)
        }
    }

    fun updateRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipeDao.insertRecipe(recipe) // REPLACE handles update
        }
    }

    fun deleteRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipeDao.deleteRecipe(recipe)
        }
    }

    fun clearAllRecipes() {
        viewModelScope.launch {
            recipeDao.deleteAll()
        }
    }
}