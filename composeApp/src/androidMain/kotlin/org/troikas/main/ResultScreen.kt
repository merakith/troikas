package org.troikas.main


//noinspection SuspiciousImport

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(barcode: String?, viewModel: ResultViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    // Load data when the screen opens
    LaunchedEffect(barcode) {
        if (barcode != null) {
            viewModel.loadProductData(barcode)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("The Clinical Curator", color = Color.Black, fontSize = 22.sp, fontWeight = FontWeight.Normal) },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: handle back */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },

                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
                .verticalScroll(state)
                .fillMaxSize()
        ) {
            when (val state = uiState) {
                is ResultUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(color = Color(0xFF4A3CB5))
                            Spacer(Modifier.height(8.dp))
                            Text("Fetching data... please wait!")
                        }
                    }
                }
                is ResultUiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = state.message, color = Color.Red, modifier = Modifier.padding(16.dp))
                    }
                }
                is ResultUiState.Success -> {
                    val product = state.product
                    Column(
                        modifier = Modifier
                            .verticalScroll(scrollState)
                            .fillMaxSize()
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color(0xFF4A3CB5))
                                .padding(20.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.Start) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(20.dp),
                                        color = Color.White.copy(alpha = 0.2f)
                                    ) {
                                        Text(
                                            "VERIFIED PRODUCT",
                                            color = Color.White,
                                            fontSize = 11.sp,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                        )
                                    }
                                }
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    product.productName ?: "Unknown Product",
                                    color = Color.White,
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.W700
                                )
                                Spacer(Modifier.height(8.dp))
                                Text("Barcode: ${barcode ?: "N/A"}", color = Color.White, fontSize = 13.sp)
                                Spacer(Modifier.height(8.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .background(Color(0xFF2ECC71), shape = CircleShape)
                                            .size(40.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "88", // Hardcoded for now, student style
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Spacer(Modifier.width(10.dp))
                                    Text(text = "Health Score: Excellent", color = Color.White)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // AI Analysis Card
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFD32F2F).copy(alpha = 0.08f)),
                            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = Color(0xFFE74C3C),//so that the label icon is red insted of black as vectors doesnt have the Color function
                                modifier = Modifier.padding(8.dp)


                            )


                        }
                        Surface(
                            color = Color(0xFFD32F2F).copy(alpha = 0.15f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                "High Risk",
                                color = Color(0xFFD32F2F),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(4.dp)

                            )
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Raw Ingredients (for that "student forgot to hide debug info" vibe)
                        Text(
                            "Raw Ingredients (Debug):", 
                            modifier = Modifier.padding(horizontal = 16.dp), 
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                        Text(
                            product.ingredientsText ?: "Nothing here",
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                            fontSize = 10.sp,
                            color = Color.Gray
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "The ingredient is shit dont eat this product ,\n" +
                                " shitty ingredients trust me dhananjay it is just for filling",
                        color = Color(0xFF757575),
                    )

                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF39C12).copy(alpha = 0.08f)),

                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    )
                    {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFF39C12).copy(alpha = 0.15f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = Color(0xFFF39C12),
                                modifier = Modifier.padding(8.dp)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFFF39C12).copy(0.15f)
                        )
                        {
                            Text(
                                text = "Moderate",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFF39C12),
                                modifier = Modifier.padding(4.dp)
                            )
                        }

                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Sunflower Oil", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFF39C12),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
                    )
                    {
                        Text(
                            "PROCESSED FAT",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "The ingredient is shit dont eat this product ,\n" +
                                " shitty ingredients trust me dhananjay it is just for filling",
                        color = Color(0xFF757575),
                    )


                }
                Spacer(modifier = Modifier.height(8.dp))


                }
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF39C12).copy(alpha = 0.08f)),

                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    )
                    {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFF39C12).copy(alpha = 0.15f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Science,
                                contentDescription = null,
                                tint = Color(0xFFF39C12),
                                modifier = Modifier.padding(8.dp)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFFF39C12).copy(0.15f)
                        )
                        {
                            Text(
                                text = "ADDITIVE",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFF39C12),
                                modifier = Modifier.padding(4.dp)
                            )
                        }

                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Tricalcium Phosphate", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFF39C12),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
                    )
                    {
                        Text(
                            "FORTIFIER",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "The ingredient is shit dont eat this product ,\n" +
                                " shitty ingredients trust me dhananjay it is just for filling",
                        color = Color(0xFF757575),
                    )


                }



            }
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF116E45).copy(
                        alpha = 0.08f
                    )
                ),

                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    )
                    {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFF116E45).copy(alpha = 0.15f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Eco,
                                contentDescription = null,
                                tint = Color(0xFF116E45),
                                modifier = Modifier.padding(8.dp)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFF116E45).copy(0.15f)
                        )
                        {
                            Text(
                                text = "Essential",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF116E45),
                                modifier = Modifier.padding(4.dp)
                            )
                        }

                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Whole Grain Oats", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFF116E45),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
                    )
                    {
                        Text(
                            "whole food",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "The ingredient is shit dont eat this product ,\n" +
                                " shitty ingredients trust me dhananjay it is just for filling",
                        color = Color(0xFF757575),
                    )


                }

            }
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF116E45).copy(
                        alpha = 0.08f
                    )
                ),

                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    )
                    {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFF116E45).copy(alpha = 0.15f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.WaterDrop,
                                contentDescription = null,
                                tint = Color(0xFF116E45),
                                modifier = Modifier.padding(8.dp)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFF116E45).copy(0.15f)
                        )
                        {
                            Text(
                                text = "PURE",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF116E45),
                                modifier = Modifier.padding(4.dp)
                            )
                        }

                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Filtered Water", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFF116E45),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
                    )
                    {
                        Text(
                            "HYDRATION",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "The ingredient is shit dont eat this product ,\n" +
                                " shitty ingredients trust me dhananjay it is just for filling",
                        color = Color(0xFF757575),
                    )


                }
            }
        }
    }
}
