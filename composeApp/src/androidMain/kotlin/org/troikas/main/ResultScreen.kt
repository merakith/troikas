package org.troikas.main

import android.R.attr.padding
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(barcode: String?){
    val state = rememberScrollState()
    Scaffold(
        topBar = {
            TopAppBar(
                title ={ Text("Smart Grahak",color = Color.White)},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue
                )
            )
        }
    ){padding ->
        Column(modifier = Modifier.padding(padding)
            .verticalScroll(state)
            .fillMaxSize()

                                ) {
            Text("barcode: $barcode")
        }

    }

}

