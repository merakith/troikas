package org.troikas.main.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.troikas.main.BuildConfig
import org.troikas.main.network.GeminiClient

class GeminiRepository{
    suspend fun analyseIngredients(ingredientsText:String): String?{
        //forces execution from UI thread to background thread pool
        return withContext(Dispatchers.IO){
            try{
                val prompt="""I scanned a product with these ingredients: $ingredientsText. Categorize all the ingredients strictly into three arrays: 'healthy', 'fine', and 'avoid'.
                CRITICAL INSTRUCTION: You must respond ONLY with a valid JSON object. Do not include markdown formatting, code blocks, or introductory text. Use this exact structure:
                {
                    "healthy": ["ingredient 1", "ingredient 2"],
                    "fine": ["ingredient 3"],
                    "avoid": ["ingredient 4"]
                }"""
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