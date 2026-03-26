package org.troikas.main.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface IngredientDao{
    // Sync tool;if supabase sends an ingredient we already have, replace it
    @Insert(onConflict=OnConflictStrategy.REPLACE)
    suspend fun insertIngredients(ingredients: List<IngredientClassification>)

    // takes a list and returns only matching rows
    @Query("SELECT * FROM ingredient_classification WHERE name IN (:ingredientNames)")
    suspend fun getClassifications(ingredientNames: List<String>): List<IngredientClassification>

    // nuke: wipe out all ingredients
    @Query("DELETE FROM ingredient_classification")
    suspend fun clearAll()
}