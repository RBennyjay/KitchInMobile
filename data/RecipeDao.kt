package com.benny.kitchinmobile.data

import androidx.room.*
import com.benny.kitchinmobile.domain.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    // Get all recipes, ordered by name.
    // Flow keeps the UI updated automatically when data changes.
    @Query("SELECT * FROM recipes ORDER BY name ASC")
    fun getAllRecipes(): Flow<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("DELETE FROM recipes")
    suspend fun deleteAll()
}