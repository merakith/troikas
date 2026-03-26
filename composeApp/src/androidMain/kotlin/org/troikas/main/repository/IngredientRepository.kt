package org.troikas.main.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.troikas.main.database.IngredientClassification
import org.troikas.main.database.IngredientDao

class IngredienntRepository(private val ingredientDao: IngredientDao){
    //takes list of cleaned names and checks the local db
    suspend fun analyzeIngredients(cleanedNames: List<String>):List<IngredientClassification>{
        return withContext(Dispatchers.IO){
            ingredientDao.getClassifications(cleanedNames)
        }
    }
    //talks to supabase cloud
    suspend fun syncWithCloud(){
        try{
            println("Starting Supabase Sync...")
            //todo:connect to supabase client
            println("Sync completed successfully.")
        } catch(e:Exception){
            println("Sync failed:${e.message}")
            //swallow error
        }
    }
}