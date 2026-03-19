package org.troikas.main.network

import com.google.gson.annotations.SerializedName

data class GeminiRequest(
    val contents: List<GeminiContent>
)

data class GeminiResponse(
    val candidates: List<GeminiCandidate>?
)

data class GeminiCandidate(
    val content: GeminiContent?
)

data class GeminiContent(
    val parts: List<GeminiPart>
)

data class GeminiPart(
    val text: String
)