package org.troikas.main.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import org.troikas.main.database.AppDatabase
import org.troikas.main.database.IngredientRepository

class SyncWorker(context: Context, workerParams: WorkerParameters) :
        CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            val database = AppDatabase.getDatabase(applicationContext)
            // manual injection: passing dao to repository
            val repo =
                    IngredientRepository(
                            ingredientDao = database.ingredientDao(),
                            supabase = org.troikas.main.network.SupabaseClient.client
                    )
            repo.syncWithCloud()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
