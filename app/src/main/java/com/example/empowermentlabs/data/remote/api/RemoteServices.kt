package com.example.empowermentlabs.data.remote.api

import com.example.empowermentlabs.data.remote.models.Recipe
import com.example.empowermentlabs.data.remote.models.RecipesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface RemoteServices {

    @GET(RECIPES)
    suspend fun getRecipes(@QueryMap queryMap: Map<String, String>): RecipesResponse

    @GET(RECIPE)
    suspend fun getRecipe(@Path("id") recipeId: String, @QueryMap queryMap: Map<String, String>): Recipe

    companion object {
        internal const val RECIPES = "recipes/complexSearch/"
        internal const val RECIPE = "recipes/{id}/information"
    }
}