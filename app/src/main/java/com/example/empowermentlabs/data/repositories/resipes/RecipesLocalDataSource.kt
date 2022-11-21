package com.example.empowermentlabs.data.repositories.resipes

import com.example.empowermentlabs.data.db.dao.RecipeItemDao
import com.example.empowermentlabs.data.models.RecipeItem
import com.example.empowermentlabs.data.toDbItemRecipe
import com.example.empowermentlabs.data.toDomainItemRecipe
import javax.inject.Inject

class RecipesLocalDataSource @Inject constructor(private val recipeDao: RecipeItemDao) {

    suspend fun isEmpty(): Boolean = recipeDao.recipeCount() == 0

    suspend fun save(recipes: List<RecipeItem>) {
        recipeDao.insertList(recipes.map { it.toDbItemRecipe() })
    }

    suspend fun update(recipe: RecipeItem) {
        recipeDao.update(recipe.toDbItemRecipe())
    }

    fun getAll(): List<RecipeItem> =
        recipeDao.getAllRecipes().map { recipes -> recipes.toDomainItemRecipe() }

    fun findByTitle(search: String): List<RecipeItem> =
        recipeDao.findByTitle(search).map { recipes -> recipes.toDomainItemRecipe() }

    fun findByFavorites(): List<RecipeItem> =
        recipeDao.findByFavorites().map { recipes -> recipes.toDomainItemRecipe() }
}