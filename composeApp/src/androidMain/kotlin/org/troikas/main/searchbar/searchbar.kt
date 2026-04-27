package org.troikas.main.searchbar

import android.graphics.drawable.Icon
import android.media.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.excludeFromSystemGesture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar



import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight


import androidx.compose.ui.unit.sp
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(navController: NavController) {
    var text by remember { mutableStateOf("") }
    Scaffold(topBar = {TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
                    .padding(end = 8.dp)
            ) {


                Surface(
                    shadowElevation = 2.dp,
                    shape = RoundedCornerShape(26.dp),
                    modifier = Modifier.weight(1f),

                ) {TextField(
                    value = text,
                    onValueChange = {text = it},
                    placeholder = {
                        Text(
                            "Enter Barcode",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    } ,
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF2F2F7),
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(26.dp),
                    modifier = Modifier.weight(1f)
                ) }
                Spacer(modifier = Modifier.width(8.dp))
                Surface(
                    shadowElevation = 2.dp,
                    shape = CircleShape,
                    modifier = Modifier.size(46.dp)
                ) {
                    Box(

                        modifier = Modifier.size(46.dp)
                            .background(
                                color = Color.White,
                                shape = CircleShape
                            )
                            .clickable{navController.navigate("result/$text")},
                        contentAlignment = Alignment.Center
                    ){
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.Black,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Surface(
                    shadowElevation = 2.dp,
                    shape = CircleShape,
                    modifier = Modifier.size(46.dp)
                ) {
                    Box(

                        modifier = Modifier.size(46.dp)
                            .background(
                                color = Color.White,
                                shape = CircleShape
                            ),

                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Search",
                            tint = Color.Black,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }
    )}

    ){padding ->
        Column( modifier = Modifier
            .padding(padding)
            .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            val cardColors = listOf(
                Color(0xFF2D2F3E),  // soft dark navy
                Color(0xFF6B3A2A),  // muted terracotta
                Color(0xFF1E3A5F)   // soft dark blue
            )
            val randomColor  = remember { cardColors.random() }

            Card(
                modifier = Modifier.fillMaxWidth()
                    .height(240 .dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor= randomColor )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ){
                    Icon(
                        imageVector = Icons.Default.DocumentScanner,
                        contentDescription = "Barcode scanner",
                        modifier = Modifier.padding(16.dp)
                            .size(36.dp)
                            .align(Alignment.TopStart),
                        tint = Color.White
                    )
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(bottom = 40.dp , end = 16.dp)
                    ) {
                        Box(modifier = Modifier
                            .size(40.dp)
                            .background(color = Color.White.copy(alpha = 0.2f), shape = CircleShape)
                            )
                        Box(modifier = Modifier
                            .size(40.dp)
                            .offset(x=(-16).dp)
                            .background(color = Color.White.copy(alpha = 0.2f), shape = CircleShape)
                        )
                    }
                    Text(
                        "Barcode Scanner",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 12.sp,
                        letterSpacing = 2.sp,

                        modifier = Modifier.padding(16.dp)
                            .align(Alignment.BottomEnd)

                    )
                }

            }
            val facts = listOf(
                "Companies use palm oil in their products to make it cheaper, even though it is more harmful than other oils.",
                "Many food colorings are derived from petroleum byproducts.",
                "Carrageenan, found in many dairy products, is linked to gut inflammation.",
                "The average person consumes around 5 grams of microplastics every week through food packaging.",
                "High fructose corn syrup is metabolized differently than regular sugar and can contribute to fatty liver disease."
            )

            val randomFact = remember { facts.random() }
            Card(
                modifier = Modifier.fillMaxWidth()
                    .height(150.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Did you know!",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        randomFact,
                        fontSize = 14.sp,
                        color = Color(0xFF757575),
                        lineHeight = 20.sp
                    )
                }
            }

            }
        }
    }



