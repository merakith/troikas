package org.troikas.main.utils

class stringParser {
    fun stringParser(rawString: String): List<String> {
        return Regex("\\((.*?)\\)")
                .replace(rawString) { matchResult ->
                    val textInside =
                            matchResult.groupValues[
                                    1] // numbers(if there are any) or whatever is nside the
                    // brackets
                    if (textInside.any { it.isDigit() }) ",$textInside"
                    else "" // if it's a number, add it, or discard it
                }
                .split(",", "-", ":", " and ", " And ", " AND ")
                .map { it.trim().lowercase() }
                .filter { it.isNotEmpty() }
    }
}
