package org.troikas.main.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.troikas.main.network.GeminiClient

class GeminiRepository{
    suspend fun analyseIngredients(ingredientsText:String): String?{
        //forces execution from UI thread to background thread pool
        return withContext(Dispatchers.IO){
            try{
                val prompt="Act as an expert nutritionist. I scanned a product with these ingredients: $ingredientsText. Are these safe to consume? Give me a concise, 2-sentence analysis."
                val requestPayload=GeminiRequest(
                    contents=listOf(
                        GeminiContent(
                            parts=listOf(GeminiPart(text=prompt))
                        )
                    )
                )
                
                val response = GeminiClient.apiService.generateContent(
                    apiKey=BuildConfig.GEMINI_API_KEY,
                    request=requestPayload
                )
                response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
            } catch(e:Exception){
                e.printStackTrace()
                null
            }
        }
    }
}