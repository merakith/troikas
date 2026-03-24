package org.troikas.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.troikas.main.ScannerUiState
import org.troikas.main.network.GeminiRepository

data class IngredientAnalysis(
    val healthy: List<String>,
    val fine: List<String>,
    val avoid: List<String>
)

sealed class ScannerUiState{
    object Idle: ScannerUiState()
    object Loading: ScannerUiState()
    data class Success(val analysis:IngredientAnalysis):ScannerUiState()
    data class Error(val message:String):ScannerUiState()
}

class ViewModel:ViewModel(){
    private val repository=FoodRepository()
    private val geminiRepository=GeminiRepository()

    private val _uiState=MutableStateFlow<ScannerUiState>(ScannerUiState.Idle)
    val uiState:StateFlow<ScannerUiState>=_uiState

    fun scanBarcode(barcode:String){
        ViewModelScope.launch{
            _uiState.value=ScannerUiState.Loading
            try{
                val product = repository.getProduct(barcode)

                if(product!=null && product.ingredientsText!=null){
                    val productName=product.productName?:"Unknown Product"
                    val finalAnalysis:IngredientAnalysis=geminiRepository.analyseIngredients(ingredientsText)
                    _uiState.value=ScannerUiState.Success(finalAnalysis)

                }else{
                    _uiState.value=ScannerUiState.Error("Product or ingredients not found.")
                }
            }catch(e:Exception){
                _uiState.value=ScannerUiState.Error("Network Error:${e.message}")
            }
        }
    }
}