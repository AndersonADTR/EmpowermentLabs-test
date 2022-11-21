package com.example.empowermentlabs.data.db

import android.content.Context
import androidx.room.*
import com.example.empowermentlabs.data.db.dao.RecipeDao
import com.example.empowermentlabs.utils.DATABASE_NAME
import com.example.empowermentlabs.data.db.dao.RecipeItemDao
import com.example.empowermentlabs.data.db.models.Recipe
import com.example.empowermentlabs.data.db.models.RecipeItem

@Database(
    entities = [RecipeItem::class, Recipe::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(StringTypeConverter::class)
abstract class EmpowermentLabsDatabase : RoomDatabase() {

    abstract fun recipeItemDao(): RecipeItemDao
    abstract fun recipeDao(): RecipeDao

    companion object {

        @Volatile
        private var INSTANCE: EmpowermentLabsDatabase? = null

        fun getDatabase(context: Context): EmpowermentLabsDatabase {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                EmpowermentLabsDatabase::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
            INSTANCE = instance
            return instance
        }
    }
}