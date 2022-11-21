package com.example.empowermentlabs.data.repositories.recipe

import com.example.empowermentlabs.data.models.Recipe
import com.example.empowermentlabs.data.models.RecipeItem
import com.example.empowermentlabs.data.remote.network.Resource
import com.example.empowermentlabs.data.remote.network.Status
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val recipeLocalDataSource: RecipeLocalDataSource,
    private val recipeRemoteDataSource: RecipeRemoteDataSource
) {
    fun requestDbRecipe(recipeId: Int): Resource<Any> =
        Resource(Status.SUCCESS, recipeLocalDataSource.findById(recipeId), null)

    fun requestDbRecipeItem(recipeId: Int): Resource<Any> =
        Resource(Status.SUCCESS, recipeLocalDataSource.findItemById(recipeId), null)

    suspend fun requestRecipe(recipeId: Int, queryMap: Map<String, String>): Resource<Any> {
        return recipeRemoteDataSource.requestRecipe(recipeId.toString(), queryMap)
    }

    suspend fun switchFavorite(recipeItem: RecipeItem) {
        val updated = recipeItem.copy(favorite = !recipeItem.favorite)
        recipeLocalDataSource.updateItem(updated)
    }

    suspend fun saveData(recipe: Recipe) {
        recipeLocalDataSource.save(recipe)
    }
}