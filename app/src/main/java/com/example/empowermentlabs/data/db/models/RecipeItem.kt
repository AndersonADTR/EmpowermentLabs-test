package com.example.empowermentlabs.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipeItem(

    val image: String? = null,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "recipe_id")
    val id: Int,

    val title: String? = null,

    val imageType: String? = null,

    val favorite: Boolean = false
)