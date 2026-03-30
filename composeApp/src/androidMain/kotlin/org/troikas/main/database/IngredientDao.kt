package org.troikas.main.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface IngredientDao{
    // Sync tool;if supabase sends an ingredient we already have, replace it
    @Insert(onConflict=OnConflictStrategy.REPLACE)
    fun insertIngredients(ingredients: List<IngredientClassification>): List<Long>

    // takes a list and returns only matching rows
    @Query("SELECT * FROM ingredient_classification WHERE name IN (:ingredientNames)")
    fun getClassifications(ingredientNames: List<String>): List<IngredientClassification>
}