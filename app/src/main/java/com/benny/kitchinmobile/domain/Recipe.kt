package com.benny.kitchinmobile.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

// --- Room Entity ---
@Entity(tableName = "recipes")
@TypeConverters(Converters::class)
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,          // Room requires a primary key
    val title: String,
    val prepTime: String,
    val ingredients: List<String>,
    val instructions: String
)

// --- TypeConverter for List<String> ---
class Converters {
    @TypeConverter
    fun fromList(list: List<String>): String = list.joinToString(",")

    @TypeConverter
    fun toList(data: String): List<String> = data.split(",").map { it.trim() }
}