package org.troikas.main.work

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object SyncScheduler{
    fun scheduleDailySync(context:Context){
        //dont sync if no internet
        val constraints= Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        //run this once every 24 hours
        val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(24,TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()
        // hand instructions to androidos. 
        // KEEP prevents overlapping background jobs
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "DailyIngredientSync",
            ExistingPeriodicWorkPolicy.KEEP,
            syncRequest
        )
    }
}