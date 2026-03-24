package org.troikas.main

import kotlinx.coroutines.runBlocking
import org.troikas.main.network.FoodRepository
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ComposeAppAndroidUnitTest {
    @Test
    fun testValidBarcode() {
        runBlocking{
            val repo=FoodRepository()
            val barcode="8904063230010"
            assertNotNull(repo.getProduct(barcode), "Error: Product info was not received!")
        }
    }

    @Test
    fun testInvalidBarcode() {
        runBlocking {
            val repo = FoodRepository()
            val barcode = "0000000000"
            assertNull(repo.getProduct(barcode), "Error: Invalid product exists?")
        }
    }
}