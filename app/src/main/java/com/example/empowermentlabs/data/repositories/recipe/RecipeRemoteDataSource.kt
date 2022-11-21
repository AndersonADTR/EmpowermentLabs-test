package com.example.empowermentlabs.data.repositories.recipe

import com.example.empowermentlabs.data.models.Recipe
import com.example.empowermentlabs.data.remote.api.RemoteServices
import com.example.empowermentlabs.data.remote.network.Resource
import com.example.empowermentlabs.data.remote.network.ResponseHandler
import com.example.empowermentlabs.data.toDomainRecipe
import com.example.empowermentlabs.ui.extensions.tryOrDefaultException
import javax.inject.Inject

class RecipeRemoteDataSource @Inject constructor(
    private val services: RemoteServices,
    private val responseHandler: ResponseHandler
) {

    suspend fun requestRecipe(recipeId: String, queryMap: Map<String, String>): Resource<Any> {
        return tryOrDefaultException<Resource<Recipe>>({
            services.getRecipe(recipeId, queryMap).toDomainRecipe()
        }, responseHandler)
    }
}