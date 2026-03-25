package org.troikas.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.troikas.main.barcode.BarcodeSearchBar
import org.troikas.main.barcode.ResultScreen

@Composable
@Preview
fun App() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "search"
    ) {
        composable("search") {
            BarcodeSearchBar(navController)
        }
        composable("result/{barcode}") { backStackEntry ->
            val barcode = backStackEntry.arguments?.getString("barcode")
            ResultScreen(barcode)
        }
    }
}