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
            println("WorkManager: Waking up to sync OpenFoodFacts & Supabase data...")
            val database = AppDatabase.getDatabase(applicationContext) // get singleton db instance
            val repo = IngredientRepository(database.ingredientDao()) // get dao and pass it to repo
            repo.syncWithCloud()
            println("WorkManager: Sync completed successfully.")
            Result.success()
        } catch (e: Exception) {
            println("WorkManager: sync failed- ${e.message}")
            Result.retry()
        }
    }
}
