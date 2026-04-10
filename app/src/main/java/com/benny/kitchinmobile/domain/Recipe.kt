package com.benny.kitchinmobile.domain

data class Recipe(
    var id: String = "",              //  Firestore document ID
    var title: String = "",
    var prepTime: String = "",
    var ingredients: List<String> = emptyList(),
    var instructions: String = ""
)