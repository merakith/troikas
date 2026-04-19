package org.troikas.main.database



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import org.troikas.main.database.IngredientClassification

class ResultViewModel : ViewModel() {

    var productName by mutableStateOf<String?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var avoidIngredients by mutableStateOf<List<IngredientClassification>>(emptyList())
        private set

    var moderateIngredients by mutableStateOf<List<IngredientClassification>>(emptyList())
        private set

    var goodIngredients by mutableStateOf<List<IngredientClassification>>(emptyList())
        private set
}