package org.troikas.main

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.troikas.main.searchbar.BarcodeScanSearchBar
import org.troikas.main.ResultScreen


@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    MaterialTheme() {
        NavHost(
            navController = navController,
            startDestination = "search"
        ) {
            composable("search") {
               BarcodeScanSearchBar(navController)
            }
            composable("result/{barcode}") { backStackEntry ->
                val barcode = backStackEntry.arguments?.getString("barcode")
                ResultScreen(barcode)
            }
        }
    }
}