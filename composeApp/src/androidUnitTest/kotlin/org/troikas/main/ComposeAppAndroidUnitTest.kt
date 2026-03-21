package org.troikas.main

import kotlin.test.Test
import kotlinx.coroutines.runBlocking

class ComposeAppAndroidUnitTest {

    @Test
    fun testOpenFoodFactsEngine() {
        runBlocking{
            println("booting repository")
            val repo=FoodRepository()

            val testBarcode="8901058002346"
            println("sending request")

            val product=repo.getProduct(testBarcode)

            println("server response:")
            if(product!=null){
                println("success")
                println("Product Name: ${product.productName}")
                println("Ingredients: ${product.ingredientsText}")
            }else{
                println("failed,product is null")
            }
            println("done")
        }
    }
}