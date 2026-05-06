package org.troikas.main.utils

fun levenshtein(a: String, b: String): Int {
    val dp = Array(a.length + 1) { IntArray(b.length + 1) }
    for (i in 0..a.length) dp[i][0] = i
    for (j in 0..b.length) dp[0][j] = j
    for (i in 1..a.length) {
        for (j in 1..b.length) {
            dp[i][j] = if (a[i - 1] == b[j - 1]) dp[i - 1][j - 1]
            else 1 + minOf(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1])
        }
    }
    return dp[a.length][b.length]
}

fun isFuzzyMatch(scanned: String, target: String): Boolean {
    if (scanned == target) return true

    val eNumberRegex = Regex("^e\\d{3,4}[a-z]?$")
    if (eNumberRegex.matches(scanned) || eNumberRegex.matches(target)) return false

    if (scanned.length < 5 || target.length < 5) return false

    val shorter = minOf(scanned.length, target.length)
    val longer = maxOf(scanned.length, target.length)
    if (longer - shorter > (shorter * 0.4).toInt().coerceAtLeast(2)) return false

    val threshold = when {
        shorter >= 12 -> 2
        shorter >= 7 -> 1
        else -> 0  // 5-6 char strings: exact only after the gates
    }
    return levenshtein(scanned, target) <= threshold
}
