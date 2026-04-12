package org.troikas.main


import android.R.attr.text
import android.graphics.drawable.Icon
import android.service.autofill.OnClickAction
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.DividerDefaults.color
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDefaults.color
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(barcode: String?){
    val state = rememberScrollState()
    Scaffold(
        topBar = {
            TopAppBar(
                title ={ Text("Smart Grahak",color = Color.Black)},
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
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
    ){padding ->
        Column(modifier = Modifier.padding(padding)
            .verticalScroll(state)
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
            ){
                Column(
                    horizontalAlignment = Alignment.Start
                ) {

                   Row(
                       modifier = Modifier.fillMaxWidth(),
                       horizontalArrangement = Arrangement.End

                   ) {
                       Surface(
                           shape = RoundedCornerShape(20.dp),
                           color = Color.White.copy(alpha = 0.2f)

                       ) {
                           Text("VERIFIED PRODUCT",
                               color = Color.White,
                               fontSize = 11.sp,
                               modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                           )
                       }
                   }
                    Spacer(Modifier.height(8.dp))
                    Text("PRODUCT NAME",color = Color.White,fontSize=28.sp, fontWeight = FontWeight.W700)
                    Spacer(Modifier.height(8.dp))
                    Text("SUBTITLE",color = Color.White,fontSize = 13.sp)
                    Spacer(Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    Color(0xFF2ECC71),
                                    shape = CircleShape
                                )
                                .size(40.dp),
                            contentAlignment = Alignment.Center
                        ){
                            Text(
                                text = "88",
                                color=Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(Modifier.width(10.dp))
                        Text(text = "Health Score: Excellent",color = Color.White)
                    }
                }
            }


        }



    }

}

