package com.example.empowermentlabs.data.repositories.recipe

import com.example.empowermentlabs.data.*
import com.example.empowermentlabs.data.db.dao.RecipeDao
import com.example.empowermentlabs.data.db.dao.RecipeItemDao
import com.example.empowermentlabs.data.models.Recipe
import com.example.empowermentlabs.data.models.RecipeItem
import javax.inject.Inject

class RecipeLocalDataSource @Inject constructor(
    private val recipeDao: RecipeDao,
    private val recipeItemDao: RecipeItemDao
) {

    suspend fun save(recipe: Recipe) {
        recipeDao.insert(recipe.toDbRecipe())
    }

    fun findById(recipeId: Int): Recipe? {
        val recipe = recipeDao.findById(recipeId)
        return recipe?.toDomainRecipe()
    }

    suspend fun updateItem(recipe: RecipeItem) {
        recipeItemDao.update(recipe.toDbItemRecipe())
    }

    fun findItemById(recipeId: Int): RecipeItem? {
        val recipe = recipeItemDao.findById(recipeId)
        return recipe?.toDomainItemRecipe()
    }
}