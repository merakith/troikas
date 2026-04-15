package org.troikas.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.troikas.main.network.FoodRepository
import org.troikas.main.network.GeminiRepository
import org.troikas.main.network.ProductDetails

sealed class ResultUiState {
    object Loading : ResultUiState()
    data class Success(
        val product: ProductDetails
    ) : ResultUiState()

    data class Error(val message: String) : ResultUiState()
}

class ResultViewModel : ViewModel() {
    private val foodRepository = FoodRepository()
    private val geminiRepository = GeminiRepository()

    private val privUiState = MutableStateFlow<ResultUiState>(ResultUiState.Loading)
    val uiState: StateFlow<ResultUiState> = privUiState.asStateFlow()

    fun loadProductData(barcode: String) {
        viewModelScope.launch {
            privUiState.value = ResultUiState.Loading
            try {
                // try network first
                val product = foodRepository.getProduct(barcode)
                if (product != null) {
                    privUiState.value = ResultUiState.Success(product)
                } else {
                    // fallback query supabase
                    val dbProduct = SupabaseClient.client.from("products").select {
                        filter { eq("barcode", barcode) }
                    }.decodeSingle<Product>()

                    privUiState.value = ResultUiState.Success(dbProduct)
                }
            } catch (e: Exception) {
                privUiState.value = ResultUiState.Error(e.message ?: "An error occurred")
            }
        }
    }
}
