package org.troikas.main.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class SyncWorker(
    context:Context,
    workerParams:WorkerParameters
): CoroutineWorker(context, workerParams){
    override suspend fun doWork():Result{
        return try{
            println("WorkManager: Waking up to sync OpenFoodFacts & Supabase data...")
            //TODO: inject IngredientRepository and call repository.syncWithCloud()
            println("WorkManager: Sync completed successfully.")
            //tells androidos: job done, go sleep 
            Result.success()
        } catch(e: Exception){
            println("WorkManager: sync failed- ${e.message}")
            //tells androidos: internet maybe down, try again later 
            Result.retry()
        }
    }
}