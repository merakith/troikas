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
        val product: ProductDetails,
        val analysis: String?
    ) : ResultUiState()
    data class Error(val message: String) : ResultUiState()
}

class ResultViewModel : ViewModel() {
    private val foodRepository = FoodRepository()
    private val geminiRepository = GeminiRepository()

    private val _uiState = MutableStateFlow<ResultUiState>(ResultUiState.Loading)
    val uiState: StateFlow<ResultUiState> = _uiState.asStateFlow()

    fun loadProductData(barcode: String) {
        viewModelScope.launch {
            _uiState.value = ResultUiState.Loading
            try {
                val product = foodRepository.getProduct(barcode)
                if (product != null) {
                    // add the supabase query here
                    _uiState.value = ResultUiState.Success(product, null)
                } else {
                    _uiState.value = ResultUiState.Error("Product not found. check your internet maybe?")
                }
            } catch (e: Exception) {
                _uiState.value = ResultUiState.Error("Oops! " + (e.message ?: "Something went wrong"))
            }
        }
    }
}
