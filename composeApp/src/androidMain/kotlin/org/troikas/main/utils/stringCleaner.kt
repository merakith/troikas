package org.troikas.main.utils

class IngredientParser{
    fun parseIngredients(rawString: String): List<String>{
        return rawString
        //1.removed bracketed content
            .replace(Regex("\\(.*?\\)"), "")   
        //2. chop list
            .split(",","-", " and ", " And ", " AND ")
        //3.trim whitespaces
            .map{it.trim()}
        //4.remove blanks
            .filter{it.isNotEmpty()}
    }
}