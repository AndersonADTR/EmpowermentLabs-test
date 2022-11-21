package com.example.empowermentlabs.data.repositories.resipes

import com.example.empowermentlabs.data.models.RecipeItem
import com.example.empowermentlabs.data.remote.api.RemoteServices
import com.example.empowermentlabs.data.remote.network.Resource
import com.example.empowermentlabs.data.remote.network.ResponseHandler
import com.example.empowermentlabs.data.toDomainItemRecipe
import com.example.empowermentlabs.ui.extensions.tryOrDefaultException
import javax.inject.Inject

class RecipesRemoteDataSource @Inject constructor(
    private val services: RemoteServices,
    private val responseHandler: ResponseHandler
) {

    suspend fun requestRecipes(queryMap: Map<String, String>): Resource<Any> {
        return tryOrDefaultException<Resource<List<RecipeItem>>>({
            val results = services.getRecipes(queryMap).results
            results.orEmpty().map { it?.toDomainItemRecipe() }
        }, responseHandler)
    }
}