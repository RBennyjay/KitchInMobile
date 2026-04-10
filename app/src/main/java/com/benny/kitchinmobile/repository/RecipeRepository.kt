//package RecipeRepository

package com.benny.kitchinmobile.repository



import android.util.Log
import com.benny.kitchinmobile.domain.Recipe
import com.google.firebase.firestore.FirebaseFirestore

class RecipeRepository {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("recipes")

    // ➕ Add Recipe
    fun addRecipe(recipe: Recipe, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val docRef = collection.document()
        recipe.id = docRef.id
        docRef.set(recipe)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }

    // ✏ Update Recipe
    fun updateRecipe(recipe: Recipe, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        collection.document(recipe.id)
            .set(recipe)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }

    //  Delete Recipe
    fun deleteRecipe(recipeId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        collection.document(recipeId)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }

    //  Real-time listener
    fun getRecipesRealtime(onUpdate: (List<Recipe>) -> Unit) {
        collection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("FIRESTORE", "Listen failed", error)
                return@addSnapshotListener
            }

            val recipes = snapshot?.documents?.mapNotNull {
                it.toObject(Recipe::class.java)
            } ?: emptyList()

            onUpdate(recipes)
        }
    }
}