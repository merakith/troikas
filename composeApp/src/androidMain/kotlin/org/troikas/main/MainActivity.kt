package org.troikas.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.troikas.main.database.IngredientRepository
import org.troikas.main.work.SyncScheduler

class MainActivity : ComponentActivity() {
    
    private val repo: IngredientRepository by inject()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        SyncScheduler.scheduleDailySync(applicationContext)
        
        lifecycleScope.launch {
            println("DEBUG: Manual Sync Starting...")
            repo.syncWithCloud()
            println("DEBUG: Manual Sync Finished.")
        }
        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}