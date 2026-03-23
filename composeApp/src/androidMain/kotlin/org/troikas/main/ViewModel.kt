package org.troikas.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.troikas.main.ScannerUiState

sealed class ScannerUiState{
    object Idle: ScannerUiState()
    object Loading: ScannerUiState()
    data class Success(val ingredients:String):ScannerUiState()
    data class Error(val message:String):ScannerUiState()
}

class ViewModel:ViewModel(){
    private val repository=OpenFoodRepository()
    private val _uiState=MutableStateFlow<ScannerUiState>(ScannerUiState.Idle)
    val uiState:StateFlow<ScannerUiState>=_uiState
    fun scanBarcode(barcode:String){
        ViewModelScope.launch{
            _uiState.value=ScannerUiState.Loading
            try{
                val product = repository.getProduct(barcode)
                if(product!=null && product.ingredientsText!-null){
                    _uiState.value=ScannerUiState.Success(product.ingredientsText)
                }else{
                    _uiState.value=ScannerUiState.Error("Product or ingredients not found.")
                }
            }catch(e:Exception){
                _uiState.value=ScannerUiState.Error("Network Error:${e.message}")
            }
            }
        }
    }
}