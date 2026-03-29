package com.benny.kitchinmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.benny.kitchinmobile.data.AppDatabase
import com.benny.kitchinmobile.domain.Recipe
import com.benny.kitchinmobile.ui.theme.KitchInMobileTheme
import com.benny.kitchinmobile.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch
import com.benny.kitchinmobile.ui.screens.RecipeListScreen



class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "kitchin-db"
        ).build()

        val viewModel = RecipeViewModel(db.recipeDao())

        setContent {
            MainScreen(viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: RecipeViewModel) {

    var isDarkMode by remember { mutableStateOf(false) }

    KitchInMobileTheme(darkTheme = isDarkMode) {

        val recipeList by viewModel.recipes.collectAsState()

        var showAddDialog by remember { mutableStateOf(false) }
        var isExpanded by remember { mutableStateOf(false) }
        var recipeToEdit by remember { mutableStateOf<Recipe?>(null) }

        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        val transition = updateTransition(targetState = isExpanded, label = "FabMenu")
        val rotation by transition.animateFloat(label = "Rotate") { if (it) 45f else 0f }

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    modifier = Modifier.width(260.dp),
                    drawerContainerColor = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.fillMaxHeight().padding(16.dp)) {

                        Text(
                            "Kitch-In",
                            modifier = Modifier.padding(vertical = 16.dp),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Black
                        )

                        HorizontalDivider()

                        NavigationDrawerItem(
                            label = { Text("Home") },
                            selected = true,
                            onClick = { scope.launch { drawerState.close() } },
                            icon = { Icon(Icons.Default.Home, null) }
                        )

                        NavigationDrawerItem(
                            label = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("Dark Mode")
                                    Switch(
                                        checked = isDarkMode,
                                        onCheckedChange = { isDarkMode = it }
                                    )
                                }
                            },
                            selected = false,
                            onClick = { isDarkMode = !isDarkMode },
                            icon = { Icon(Icons.Default.Settings, null) }
                        )

                        NavigationDrawerItem(
                            label = { Text("Clear All Recipes", color = Color.Red) },
                            selected = false,
                            onClick = {
                                viewModel.clearAllRecipes()
                                scope.launch { drawerState.close() }
                            },
                            icon = { Icon(Icons.Default.Delete, null, tint = Color.Red) }
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            "Version 1.0.0",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        ) {

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = { KitchInTopBar { scope.launch { drawerState.open() } } },
                floatingActionButton = {
                    Column(horizontalAlignment = Alignment.End) {

                        if (isExpanded) {
                            SmallFloatingActionButton(
                                onClick = {
                                    isExpanded = false
                                    showAddDialog = true
                                },
                                containerColor = Color.White,
                                contentColor = Color(0xFFFF6B4E)
                            ) { Icon(Icons.Default.Add, contentDescription = "Add") }
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        FloatingActionButton(
                            onClick = { isExpanded = !isExpanded },
                            containerColor = Color(0xFFFF6B4E),
                            contentColor = Color.White
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.rotate(rotation))
                        }
                    }
                }
            )

            { innerPadding ->

                RecipeListScreen(
                    recipes = recipeList,
                    onDeleteRecipe = { recipe -> viewModel.deleteRecipe(recipe) },
                    onEditRecipe = { recipe -> recipeToEdit = recipe },
                    modifier = Modifier.padding(innerPadding)
                )

                if (recipeToEdit != null) {
                    EditRecipeDialog(
                        recipe = recipeToEdit!!,
                        onDismiss = { recipeToEdit = null },
                        onSave = {
                            val ingredients = it.ingredients.map { ing -> ing.replaceFirstChar { c -> c.uppercase() } }
                            viewModel.updateRecipe(it.copy(ingredients = ingredients))
                            recipeToEdit = null
                        }
                    )
                }

                if (showAddDialog) {
                    AddRecipeDialog(
                        onDismiss = { showAddDialog = false },
                        onSave = {
                            val ingredients = it.ingredients.map { ing -> ing.replaceFirstChar { c -> c.uppercase() } }

                            viewModel.addRecipe(
                                it.title,
                                it.prepTime,
                                it.ingredients.joinToString(","), //  String
                                it.instructions)
                            showAddDialog = false
                        }
                    )
                }
            }
        }
    }
}


// --- Add/Edit Dialogs ---

@Composable
fun AddRecipeDialog(onDismiss: () -> Unit, onSave: (Recipe) -> Unit) {
    var title by remember { mutableStateOf("") }
    var prepTime by remember { mutableStateOf("") }
    var ingredientsText by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("Instructions...") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New Kitch-In Recipe", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(title, { title = it }, label = { Text("Recipe Title") }, singleLine = true)
                OutlinedTextField(prepTime, { prepTime = it }, label = { Text("Time (e.g. 30 mins)") }, singleLine = true)
                OutlinedTextField(ingredientsText, { ingredientsText = it }, label = { Text("Ingredients") }, placeholder = { Text("Rice, Tomato, Onion...") })
                OutlinedTextField(instructions, { instructions = it }, label = { Text("Instructions") })
            }
        },
        confirmButton = {
            Button(onClick = {
                if (title.isNotBlank()) {
                    val ingredients = ingredientsText.split(",").map { it.trim().replaceFirstChar { c -> c.uppercase() } }
                    onSave(Recipe(title = title, prepTime = prepTime, ingredients = ingredients, instructions = instructions))
                }
            }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6B4E))) {
                Text("Save")
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}

@Composable
fun EditRecipeDialog(recipe: Recipe, onDismiss: () -> Unit, onSave: (Recipe) -> Unit) {
    var title by remember { mutableStateOf(recipe.title) }
    var prepTime by remember { mutableStateOf(recipe.prepTime) }
    var ingredientsText by remember { mutableStateOf(recipe.ingredients.joinToString(", ")) }
    var instructions by remember { mutableStateOf(recipe.instructions) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit ${recipe.title}", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(title, { title = it }, label = { Text("Recipe Title") })
                OutlinedTextField(prepTime, { prepTime = it }, label = { Text("Prep Time") })
                OutlinedTextField(ingredientsText, { ingredientsText = it }, label = { Text("Ingredients") })
                OutlinedTextField(instructions, { instructions = it }, label = { Text("Instructions") })
            }
        },
        confirmButton = {
            Button(onClick = {
                val ingredients = ingredientsText.split(",").map { it.trim().replaceFirstChar { c -> c.uppercase() } }
                onSave(recipe.copy(title = title, prepTime = prepTime, ingredients = ingredients, instructions = instructions))
            }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6B4E))) {
                Text("Update")
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}

// --- Top App Bar ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KitchInTopBar(onMenuClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                "Kitch-In Recipes",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Black,
                    letterSpacing = (-0.5).sp
                )
            )
        },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        }
    )
}