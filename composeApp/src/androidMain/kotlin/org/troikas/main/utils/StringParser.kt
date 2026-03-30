package org.troikas.main.utils

class IngredientParser{
    fun parseIngredients(rawString: String): List<String>{
        return rawString
            .replace("(",",")
            .replace("[",",")   
            .replace("{",",")   
            .replace(")",",")   
            .replace("}",",")   
            .replace("]",",")      
            .split(",","-", " and ", " And ", " AND ", ":")
            .map{it.trim().lowercase()}
            .filter{it.isNotEmpty()}
    }
}