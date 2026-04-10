package com.benny.kitchinmobile.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.benny.kitchinmobile.domain.Recipe
import com.benny.kitchinmobile.repository.RecipeRepository

class RecipeViewModel(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _recipes = mutableStateOf<List<Recipe>>(emptyList())
    val recipes: State<List<Recipe>> = _recipes

    init {
        //  Real-time updates from Firestore
        repository.getRecipesRealtime { updatedList ->
            _recipes.value = updatedList
        }
    }

    // ➕ Add Recipe
    fun addRecipe(title: String, prepTime: String, ingredients: String, instructions: String) {

        Log.d("VIEWMODEL", "addRecipe called")
        val newRecipe = Recipe(
            title = title,
            prepTime = prepTime,
            ingredients = ingredients.split(",").map { it.trim() },
            instructions = instructions
        )

        repository.addRecipe(newRecipe,
            onSuccess = { Log.d("VIEWMODEL", "Recipe added successfully") },
            onFailure = { e -> Log.e("VIEWMODEL", "Add failed", e) }
        )
    }

    // ✏ Update Recipe
    fun updateRecipe(recipe: Recipe) {
        repository.updateRecipe(recipe,
            onSuccess = { Log.d("VIEWMODEL", "Recipe updated successfully") },
            onFailure = { e -> Log.e("VIEWMODEL", "Update failed", e) }
        )
    }

    //  Delete Recipe
    fun deleteRecipe(recipe: Recipe) {
        repository.deleteRecipe(recipe.id,
            onSuccess = { Log.d("VIEWMODEL", "Recipe deleted successfully") },
            onFailure = { e -> Log.e("VIEWMODEL", "Delete failed", e) }
        )
    }
}