package com.example.empowermentlabs.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.empowermentlabs.data.db.models.Recipe

@Dao
interface RecipeDao : BaseDao<Recipe> {

    @Query("SELECT * FROM Recipe WHERE recipe_id = :id")
    fun findById(id: Int): Recipe?
}