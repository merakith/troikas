package org.troikas.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.troikas.main.database.AppDatabase
import org.troikas.main.database.IngredientRepository
import org.troikas.main.work.SyncScheduler

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        SyncScheduler.scheduleDailySync(applicationContext)
        val db = AppDatabase.getDatabase(applicationContext)
        val repo = IngredientRepository(db.ingredientDao())
        
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