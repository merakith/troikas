package org.troikas.main

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.troikas.main.database.Category
import org.troikas.main.database.DatabaseProvider
import org.troikas.main.database.IngredientClassification

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    barcode: String?,
    navController: NavController
) {
    // Phase 5 — get context, viewmodel, dao
    val context   = LocalContext.current
    val viewModel: ResultViewModel = viewModel()
    val dao       = DatabaseProvider.getDatabase(context).ingredientDao()
    val state     = rememberScrollState()

    // Phase 5 — LaunchedEffect triggers loadProduct once when screen opens
    LaunchedEffect(barcode) {
        if (!barcode.isNullOrEmpty()) {
            viewModel.loadProduct(barcode, dao)
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
                            contentDescription = "Back"
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
                .verticalScroll(state)
                .fillMaxSize()
        ) {
            Spacer(Modifier.height(8.dp))

            // Purple Header
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

                    // Phase 6 — real product name from ViewModel
                    Text(
                        viewModel.productName ?: (barcode ?: "Loading..."),
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.W700
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        viewModel.ingredientsText?.take(60)?.plus("...") ?: "Fetching details...",
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

            // Phase 6 — loading spinner
            if (viewModel.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF4A3CB5))
                }
                return@Column
            }

            // Phase 6 — error message
            viewModel.error?.let { err ->
                Text(
                    err,
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }

            // Phase 6 — real ingredient sections from ViewModel
            if (viewModel.avoidIngredients.isNotEmpty()) {
                SectionHeader("Ingredients to Avoid", Color(0xFFD32F2F))
                viewModel.avoidIngredients.forEach { item ->
                    IngredientCard(item)
                }
                Spacer(Modifier.height(8.dp))
            }

            if (viewModel.moderateIngredients.isNotEmpty()) {
                SectionHeader("Low Consumption", Color(0xFFF39C12))
                viewModel.moderateIngredients.forEach { item ->
                    IngredientCard(item)
                }
                Spacer(Modifier.height(8.dp))
            }

            if (viewModel.goodIngredients.isNotEmpty()) {
                SectionHeader("Good For You", Color(0xFF116E45))
                viewModel.goodIngredients.forEach { item ->
                    IngredientCard(item)
                }
            }

            // empty state
            if (!viewModel.isLoading &&
                viewModel.avoidIngredients.isEmpty() &&
                viewModel.moderateIngredients.isEmpty() &&
                viewModel.goodIngredients.isEmpty() &&
                viewModel.error == null
            ) {
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