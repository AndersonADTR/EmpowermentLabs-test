package com.example.empowermentlabs.di

import android.content.Context
import com.example.empowermentlabs.data.db.EmpowermentLabsDatabase
import com.example.empowermentlabs.data.db.dao.RecipeDao
import com.example.empowermentlabs.data.db.dao.RecipeItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): EmpowermentLabsDatabase {
        return EmpowermentLabsDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun providesRecipesDao(db: EmpowermentLabsDatabase): RecipeItemDao = db.recipeItemDao()

    @Singleton
    @Provides
    fun providesRecipeDao(db: EmpowermentLabsDatabase): RecipeDao = db.recipeDao()
}