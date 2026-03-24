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
            val barcode="8901058002346"
            assertNotNull(repo.getProduct(barcode), "Product info successfully received!")
        }
    }

    @Test
    fun testInvalidBarcode() {
        runBlocking {
            val repo = FoodRepository()
            val barcode = "0000000000"
            assertNull(repo.getProduct(barcode), "Invalid product doesn't exist!")
        }
    }
}