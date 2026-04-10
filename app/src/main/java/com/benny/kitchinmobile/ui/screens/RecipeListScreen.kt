package com.benny.kitchinmobile.ui.screens


import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.benny.kitchinmobile.domain.Recipe
import com.benny.kitchinmobile.ui.components.RecipeCard

@Composable
fun RecipeListScreen(
    recipes: List<Recipe>,
    onDeleteRecipe: (Recipe) -> Unit,
    onEditRecipe: (Recipe) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var recipeToDelete by remember { mutableStateOf<Recipe?>(null) }
    val focusManager = LocalFocusManager.current

    //  Filter logic
    val filteredRecipes = remember(searchQuery, recipes) {
        recipes.filter { recipe ->
            val titleMatch = recipe.title.contains(searchQuery, ignoreCase = true)
            val ingredientMatch = recipe.ingredients.any {
                it.contains(searchQuery, ignoreCase = true)
            }
            titleMatch || ingredientMatch
        }
    }

    //  Delete Confirmation Dialog
    if (recipeToDelete != null) {
        AlertDialog(
            onDismissRequest = { recipeToDelete = null },
            title = { Text("Delete Recipe?", fontWeight = FontWeight.Bold) },
//            text = { Text("Are you sure you want to remove '${recipeToDelete?.title}'?") },
            text = { Text("Are you sure you want to remove '${recipeToDelete?.title ?: ""}'?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        recipeToDelete?.let { onDeleteRecipe(it) }
                        recipeToDelete = null
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color(0xFFD32F2F)
                    )
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { recipeToDelete = null }) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {

        //  SEARCH BAR
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            placeholder = { Text("Search by title or ingredient...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray)
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = {
                        searchQuery = ""
                        focusManager.clearFocus()
                    }) {
                        Icon(Icons.Default.Close, contentDescription = "Clear search")
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFFF6B4E),
                unfocusedBorderColor = Color.LightGray
            )
        )

        //  RESULT COUNT
        Text(
            text = if (searchQuery.isEmpty())
                "${recipes.size} recipes total"
            else
                "${filteredRecipes.size} results found for \"$searchQuery\"",
            style = MaterialTheme.typography.labelLarge.copy(
                color = Color.Gray,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.5.sp
            ),
            modifier = Modifier.padding(start = 20.dp, bottom = 8.dp)
        )

        //  CONTENT AREA (SCROLLABLE)
        if (filteredRecipes.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(bottom = 100.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "No recipes found",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.LightGray
                    )
                    Text(
                        text = "Try searching for a different ingredient!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f) //  enables proper scrolling
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 120.dp) // space for FAB
            ) {
                items(filteredRecipes) { recipe ->
                    RecipeCard(
                        recipe = recipe,
                        onEdit = {
                            focusManager.clearFocus()
                            onEditRecipe(recipe)
                        },
                        onDelete = {
                            focusManager.clearFocus()
                            recipeToDelete = recipe
                        }
                    )
                }
            }
        }
    }
}