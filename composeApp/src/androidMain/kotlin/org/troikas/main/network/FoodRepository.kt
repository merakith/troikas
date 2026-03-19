package org.troikas.main.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.troikas.main.network.RetrofitClient

class FoodRepository{
    suspend fun getProduct(barcode:String): ProductDetails?{
        //runs the following block on the IO dispatcher (background thread pool)
        return withContext(Dispatchers.IO){
            try{
                val response = RetrofitClient.apiService.getProductByBarcode(barcode)
                response.product
            } catch(e:Exception){
                e.printStackTrace()
                null
            }
        }
    }
}