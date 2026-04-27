package org.troikas.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.troikas.main.database.Category
import org.troikas.main.database.IngredientClassification

// ── Helper functions ──────────────────────────────────────────────────────────
fun categoryColor(category: Category): Color = when (category) {
    Category.AVOID    -> Color(0xFFD32F2F)
    Category.MODERATE -> Color(0xFFF39C12)
    Category.HEALTHY  -> Color(0xFF116E45)
}

fun categoryIcon(category: Category): ImageVector = when (category) {
    Category.AVOID    -> Icons.Default.Warning
    Category.MODERATE -> Icons.Default.Info
    Category.HEALTHY  -> Icons.Default.CheckCircle
}

fun categoryRiskLabel(category: Category): String = when (category) {
    Category.AVOID    -> "HIGH RISK"
    Category.MODERATE -> "MODERATE"
    Category.HEALTHY  -> "GOOD"
}

// ── Section Header ────────────────────────────────────────────────────────────
@Composable
fun SectionHeader(title: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(20.dp)
                .background(color, RoundedCornerShape(2.dp))
        )
        Spacer(Modifier.width(8.dp))
        Text(
            title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

// ── Ingredient Card ───────────────────────────────────────────────────────────
@Composable
fun IngredientCard(item: IngredientClassification) {
    val color = categoryColor(item.category)
    val icon  = categoryIcon(item.category)
    val risk  = categoryRiskLabel(item.category)

    Card(
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.08f)
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = color.copy(alpha = 0.15f)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = color.copy(alpha = 0.15f)
                ) {
                    Text(
                        risk,
                        color = color,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            Text(
                item.name.replaceFirstChar { it.uppercase() },
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
            Spacer(Modifier.height(6.dp))
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = color
            ) {
                Text(
                    item.reason.uppercase(),
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                item.reason,
                color = Color(0xFF757575),
                fontSize = 13.sp,
                lineHeight = 19.sp
            )
        }
    }
}

// ── Main ResultScreen ─────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    barcode: String?,
    navController: NavController,
    viewModel: ResultViewModel = viewModel(factory=ResultViewModelFactory(androidx.compose.ui.platform.LocalContext.current))
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(barcode) {
        if (!barcode.isNullOrEmpty()) {
            viewModel.loadProductData(barcode)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Smart Grahak",
                        color = Color.Black,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Normal
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",


                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(scrollState)
                .fillMaxSize()
        ) {
            when (val state = uiState) {

                is ResultUiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(color = Color(0xFF4A3CB5))
                            Spacer(Modifier.height(8.dp))
                            Text("Fetching product data...")
                        }
                    }
                }

                is ResultUiState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            state.message,
                            color = Color.Red,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                is ResultUiState.Success -> {
                    val product = state.product

                    Spacer(Modifier.height(8.dp))

                    // Purple product header
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
                                        modifier = Modifier.padding(
                                            horizontal = 10.dp,
                                            vertical = 4.dp
                                        )
                                    )
                                }
                            }
                            Spacer(Modifier.height(8.dp))
                            Text(
                                product.product.name ?: "Unknown Product",
                                color = Color.White,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.W700
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                "Barcode: ${barcode ?: "N/A"}",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 13.sp
                            )
                            Spacer(Modifier.height(12.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .background(Color(0xFF2ECC71), shape = CircleShape)
                                        .size(40.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        "88",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(Modifier.width(10.dp))
                                Text("Health Score: Excellent", color = Color.White)
                            }
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    // Split classifications by category
                    val avoid    = product.classification.filter { it.category == Category.AVOID }
                    val moderate = product.classification.filter { it.category == Category.MODERATE }
                    val healthy  = product.classification.filter { it.category == Category.HEALTHY }

                    if (avoid.isNotEmpty()) {
                        SectionHeader("Ingredients to Avoid", Color(0xFFD32F2F))
                        avoid.forEach { IngredientCard(it) }
                        Spacer(Modifier.height(8.dp))
                    }

                    if (moderate.isNotEmpty()) {
                        SectionHeader("Low Consumption", Color(0xFFF39C12))
                        moderate.forEach { IngredientCard(it) }
                        Spacer(Modifier.height(8.dp))
                    }

                    if (healthy.isNotEmpty()) {
                        SectionHeader("Good For You", Color(0xFF116E45))
                        healthy.forEach { IngredientCard(it) }
                    }

                    if (avoid.isEmpty() && moderate.isEmpty() && healthy.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "No ingredient data found in database.",
                                color = Color(0xFF757575)
                            )
                        }
                    }

                    Spacer(Modifier.height(24.dp))
                }
            }
        }
    }
}