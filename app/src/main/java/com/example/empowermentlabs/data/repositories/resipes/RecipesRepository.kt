package com.example.empowermentlabs.data.repositories.resipes

import com.example.empowermentlabs.data.models.RecipeItem
import com.example.empowermentlabs.data.remote.network.Resource
import com.example.empowermentlabs.data.remote.network.Status
import javax.inject.Inject

class RecipesRepository @Inject constructor(
    private val recipesLocalDataSource: RecipesLocalDataSource,
    private val recipesRemoteDataSource: RecipesRemoteDataSource
) {

    fun requestDbRecipes(): Resource<Any> =
        Resource(Status.SUCCESS, recipesLocalDataSource.getAll(), null)

    suspend fun requestRecipes(queryMap: Map<String, String>): Resource<Any> {
        return recipesRemoteDataSource.requestRecipes(queryMap)
    }

    fun findByTitle(search: String): Resource<Any> =
        Resource(Status.SUCCESS, recipesLocalDataSource.findByTitle(search), null)

    fun findByFavorites(): Resource<Any> =
        Resource(Status.SUCCESS, recipesLocalDataSource.findByFavorites(), null)

    suspend fun switchFavorite(recipe: RecipeItem) {
        val updated = recipe.copy(favorite = !recipe.favorite)
        recipesLocalDataSource.update(updated)
    }

    suspend fun saveData(recipes: List<RecipeItem>) {
        recipesLocalDataSource.save(recipes)
    }
}