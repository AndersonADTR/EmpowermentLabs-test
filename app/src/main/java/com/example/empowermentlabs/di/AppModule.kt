package com.example.empowermentlabs.di

import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import com.example.empowermentlabs.data.db.dao.RecipeDao
import com.example.empowermentlabs.utils.SHARED_PREFERENCES_NAME
import com.example.empowermentlabs.data.remote.api.RemoteServices
import com.example.empowermentlabs.data.db.dao.RecipeItemDao
import com.example.empowermentlabs.data.remote.network.ResponseHandler
import com.example.empowermentlabs.data.repositories.recipe.RecipeLocalDataSource
import com.example.empowermentlabs.data.repositories.recipe.RecipeRemoteDataSource
import com.example.empowermentlabs.data.repositories.recipe.RecipeRepository
import com.example.empowermentlabs.data.repositories.resipes.RecipesLocalDataSource
import com.example.empowermentlabs.data.repositories.resipes.RecipesRemoteDataSource
import com.example.empowermentlabs.data.repositories.resipes.RecipesRepository
import com.example.empowermentlabs.utils.SharedPreferencesHelper
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSharedPreferencesHelper(@ApplicationContext context: Context): SharedPreferencesHelper {
        return SharedPreferencesHelper(
            context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE), Gson()
        )
    }

    @Singleton
    @Provides
    fun provideSettingsPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideContentResolver(@ApplicationContext context: Context): ContentResolver = context.contentResolver

    @Singleton
    @Provides
    fun provideRecipesRepository(
        recipesLocalDataSource: RecipesLocalDataSource,
        recipesRemoteDataSource: RecipesRemoteDataSource
    ): RecipesRepository = RecipesRepository(recipesLocalDataSource, recipesRemoteDataSource)

    @Singleton
    @Provides
    fun provideRecipesLocalDataSource(recipeDao: RecipeItemDao): RecipesLocalDataSource =
        RecipesLocalDataSource(recipeDao)

    @Singleton
    @Provides
    fun provideRecipesRemoteDataSource(
        services: RemoteServices,
        responseHandler: ResponseHandler
    ): RecipesRemoteDataSource = RecipesRemoteDataSource(services, responseHandler)

    @Singleton
    @Provides
    fun provideRecipeRepository(
        recipeLocalDataSource: RecipeLocalDataSource,
        recipeRemoteDataSource: RecipeRemoteDataSource
    ): RecipeRepository = RecipeRepository(recipeLocalDataSource, recipeRemoteDataSource)

    @Singleton
    @Provides
    fun provideRecipeLocalDataSource(recipeDao: RecipeDao, recipeItemDao: RecipeItemDao): RecipeLocalDataSource =
        RecipeLocalDataSource(recipeDao, recipeItemDao)

    @Singleton
    @Provides
    fun provideRecipeRemoteDataSource(
        services: RemoteServices,
        responseHandler: ResponseHandler
    ): RecipeRemoteDataSource = RecipeRemoteDataSource(services, responseHandler)
}