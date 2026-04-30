package org.troikas.main.database

import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.troikas.main.utils.isFuzzyMatch

class IngredientRepository(
    private val ingredientDao: IngredientDao,
    private val supabase: io.github.jan.supabase.SupabaseClient = org.troikas.main.network.SupabaseClient.client
) {
    suspend fun analyzeIngredients(cleanedNames: List<String>): List<IngredientClassification> {
        return withContext(Dispatchers.IO) { ingredientDao.getClassifications(cleanedNames) }
    }

    suspend fun getFuzzyClassification(scannedNames: List<String>): List<IngredientClassification> {
        return withContext(Dispatchers.IO) {
            // Try local first
            val allLocal = ingredientDao.getAllIngredients()
            val localResults = allLocal.filter { localItem ->
                scannedNames.any { scanned ->
                    val targets = listOf(localItem.name) + (localItem.synonyms ?: emptyList())
                    targets.any { isFuzzyMatch(scanned, it) }
                }
            }

            // If local is empty, fall back to Supabase
            localResults.ifEmpty {
                try {
                    supabase.from("ingredients").select {
                        filter {
                            or {
                                isIn("name", scannedNames)
                                overlaps("synonyms", scannedNames)
                            }
                        }
                    }.decodeList<IngredientClassification>()
                } catch (e: Exception) {
                    android.util.Log.e("IngredientRepo", "Supabase fallback failed: ${e.message}")
                    emptyList()
                }
            }
        }
    }

    suspend fun syncWithCloud() {
        withContext(Dispatchers.IO) {
            try {
                val lastSync = ingredientDao.getLastUpdatedAt() ?: "1970-01-01T00:00:00Z"
                val response =
                    supabase.postgrest["ingredients"]
                        .select {
                            filter {
                                gt("updated_at", lastSync) // greater than last sync time
                            }
                        }
                        .decodeList<IngredientClassification>()
                if (response.isNotEmpty()) {
                    ingredientDao.insertIngredients(response)
                    println("Inserted ${response.size} new records into local database.")
                } else {
                    println("No new records to sync.")
                }
            } catch (e: Exception) {
                println("Sync failed: ${e.message}")
                throw e
            }
        }
    }
}
