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
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(barcode: String?){
    val state = rememberScrollState()
    Scaffold(
        topBar = {
            TopAppBar(
                title ={ Text("The Clinical Curator",color = Color.Black, fontSize = 22.sp, fontWeight = FontWeight.Normal)},
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },

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
            Spacer(modifier = Modifier.height(8.dp))
            Card(colors =CardDefaults.cardColors(containerColor = Color(0xFFD32F2F).copy(alpha = 0.08f)),
                modifier = Modifier.padding(horizontal = 16.dp),

                shape = RoundedCornerShape(12.dp)) {
                Column(modifier = Modifier.padding(16.dp)){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween

                    ){
                        Surface(shape = RoundedCornerShape(8.dp),
                            color =Color(0xFFE74C3C).copy(alpha =0.15f)) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint =  Color(0xFFE74C3C), //so that the label icon is red instead of black as vectors doesn't have the Color function
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        Surface(color = Color(0xFFD32F2F).copy(alpha = 0.15f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text("High Risk",
                                color = Color(0xFFD32F2F),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                )
                        }

                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Ingredient Name", fontWeight = FontWeight.Bold  )
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFFD32F2F),
                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp)) {
                        Text(text ="INFLAMMATORY",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(4.dp))


                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("The ingredient is shit dont eat this product ,\n" +
                            " shitty ingredients trust me dhananjay it is just for filling",color = Color(0xFF757575),)

                }
            }

        }



    }

}

