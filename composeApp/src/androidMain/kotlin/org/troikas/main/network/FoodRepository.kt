package org.troikas.main.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodRepository{
    suspend fun getProduct(barcode:String): ProductDetails?{
        //forces execution to background thread pool
        return withContext(Dispatchers.IO){
            try{
                val response = FoodClient.apiService.getProductByBarcode(barcode)
                response.product
            } catch(e:Exception){
                e.printStackTrace()
                null
            }
        }
    }
}