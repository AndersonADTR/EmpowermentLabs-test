package com.example.empowermentlabs.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.empowermentlabs.data.db.models.RecipeItem

@Dao
interface RecipeItemDao : BaseDao<RecipeItem> {

    @Query("SELECT * FROM RecipeItem ORDER BY recipe_id ASC")
    fun getAllRecipes(): List<RecipeItem>

    @Query("SELECT * FROM RecipeItem WHERE title LIKE :search ORDER BY recipe_id ASC")
    fun findByTitle(search: String): List<RecipeItem>

    @Query("SELECT * FROM RecipeItem WHERE favorite = 1 ORDER BY recipe_id ASC")
    fun findByFavorites(): List<RecipeItem>

    @Query("SELECT * FROM RecipeItem WHERE recipe_id = :id")
    fun findById(id: Int): RecipeItem?

    @Query("SELECT COUNT(recipe_id) FROM RecipeItem")
    suspend fun recipeCount(): Int
}