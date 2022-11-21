package com.example.empowermentlabs.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Recipe(

    val instructions: String? = null,

    val sustainable: Boolean? = null,

    val glutenFree: Boolean? = null,

    val veryPopular: Boolean? = null,

    val title: String? = null,

    val aggregateLikes: Int? = null,

    val readyInMinutes: Int? = null,

    val sourceUrl: String? = null,

    val creditsText: String? = null,

    val dairyFree: Boolean? = null,

    val servings: Int? = null,

    val vegetarian: Boolean? = null,

    val whole30: Boolean? = null,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "recipe_id")
    val id: Int = 0,

    val imageType: String? = null,

    val summary: String? = null,

    val image: String? = null,

    val veryHealthy: Boolean? = null,

    val vegan: Boolean? = null,

    val cheap: Boolean? = null,

    val dishTypes: List<String> = emptyList(),

    val gaps: String? = null,

    val lowFodmap: Boolean? = null,

    val license: String? = null,

    val weightWatcherSmartPoints: Int? = null,

    val sourceName: String? = null,

    val spoonacularSourceUrl: String? = null,

    val ketogenic: Boolean? = null
)