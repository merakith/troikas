package org.troikas.main.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.troikas.main.database.IngredientRepository

class SyncWorker(context: Context, workerParams: WorkerParameters) :
        CoroutineWorker(context, workerParams), KoinComponent {
            
    private val repo: IngredientRepository by inject()
    
    override suspend fun doWork(): Result {
        return try {
            repo.syncWithCloud()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
