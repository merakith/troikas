package org.troikas.main.utils

class StringParser {
        fun execute(rawString: String): List<String> {
                return rawString
                        // Remove percentage annotations like "39%", "12.5%"
                        .replace(Regex("\\d+(\\.\\d+)?%"), "")
                        // Normalize curly braces to commas
                        .replace("{", ",")
                        .replace("}", ",")
                        // Normalize parentheses to commas
                        .replace("(", ",")
                        .replace(")", ",")
                        // Split on commas and periods only — NOT hyphens
                        .split(",", ".")
                        .map { it.trim().lowercase() }
                        // Filter noise
                        .filter { token ->
                                token.isNotEmpty()
                                        && token != "permitted"
                                        && token != "added"
                                        && token != "contains"
                                        // Drop pure group headers like "whole grains", "seeds", "nuts"
                                        // that are just category labels with no DB entry
                                        && token.length > 2
                        }
        }
}