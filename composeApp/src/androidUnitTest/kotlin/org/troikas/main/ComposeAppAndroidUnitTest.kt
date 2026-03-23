package org.troikas.main

import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlinx.coroutines.runBlocking

class ComposeAppAndroidUnitTest {

    @Test
    fun testOpenFoodFactsEngine() {
        runBlocking{
            println("booting repository")
            val repo=FoodRepository()

            val testBarcode="8901058017687"
            println("sending request")

            val product=repo.getProduct(testBarcode)
            
            println("server response:")
            assertNotNull("Fatal Error: Product returned null",product)
            assertNotNull("Data Error: Ingredients text is missing",product?.ingredientsText)
            
            val ingredients=product!!.ingredientsText!!.lowercase()
            assertTrue(
                "validation error:ingredients ($ingredients) did not contain expected data.",
                ingredients.contains("wheat") || ingredients.contains("floor") || ingredients.length>10
            )
            println("done")
        }
    }
}