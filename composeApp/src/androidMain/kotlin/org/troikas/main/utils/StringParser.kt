package org.troikas.main.utils

class StringParser {
    fun execute(rawString: String): List<String> {
        return rawString
                .replace("(", ", ")
                .replace(")", ", ")
                .split(
                        ",",
                        ".",
                        "-",
                        ":",
                        " and ",
                        " And ",
                        " AND ",
                        "contains",
                        "CONTAINS",
                        "Contains"
                )
                .map { it.trim().lowercase() }
                .filter { it.isNotEmpty() && it != "permitted" && it != "added" }
    }
}
