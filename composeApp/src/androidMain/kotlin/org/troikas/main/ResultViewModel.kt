package org.troikas.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.troikas.main.utils.AnalyzeProductUseCase
import org.troikas.main.utils.ProductUseCase
import org.troikas.main.database.AppDatabase
import org.troikas.main.database.IngredientRepository
import org.troikas.main.network.FoodRepository
import org.troikas.main.utils.StringParser

sealed class ResultUiState {
    object Loading : ResultUiState()
    data class Success(val product: ProductUseCase) : ResultUiState()
    data class Error(val message: String) : ResultUiState()
}

class ResultViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {

            val database= AppDatabase.getDatabase(context)
            val ingredientRepository = IngredientRepository(database.ingredientDao())
            val foodRepository = FoodRepository()
            val analyzeProductUseCase = AnalyzeProductUseCase(foodRepository, ingredientRepository, StringParser())

            @Suppress("UNCHECKED_CAST")
            return ResultViewModel(analyzeProductUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class ResultViewModel(private val analyzeProductUseCase: AnalyzeProductUseCase) : ViewModel() {

    private val privUiState = MutableStateFlow<ResultUiState>(ResultUiState.Loading)
    val uiState: StateFlow<ResultUiState> = privUiState.asStateFlow()

    fun loadProductData(barcode: String) {
        viewModelScope.launch {
            privUiState.value = ResultUiState.Loading
            try {
                val product = analyzeProductUseCase.execute(barcode)
                if (product != null) {
                    privUiState.value = ResultUiState.Success(product)
                } else {
                    privUiState.value = ResultUiState.Error("Product not found in any database")
                }
            } catch (e: Exception) {
                privUiState.value = ResultUiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
}
