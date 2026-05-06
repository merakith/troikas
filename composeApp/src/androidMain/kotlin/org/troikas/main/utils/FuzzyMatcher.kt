package org.troikas.main.utils

fun levenshtein(a: String, b: String): Int {
    val dp = Array(a.length + 1) { IntArray(b.length + 1) }

    // Base case: transforming a prefix of a into empty string = delete all chars
    for (i in 0..a.length) dp[i][0] = i
    // Base case: transforming empty string into a prefix of b = insert all chars
    for (j in 0..b.length) dp[0][j] = j

    for (i in 1..a.length) {
        for (j in 1..b.length) {
            dp[i][j] = if (a[i-1] == b[j-1]) {
                dp[i-1][j-1]
            } else {
                1 + minOf(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])
            }
        }
    }
    return dp[a.length][b.length]
}

fun isFuzzyMatch(scanned: String, target: String, threshold: Int = 2): Boolean {
    // Exact match — skip expensive computation
    if (scanned == target) return true
    // Length difference already exceeds threshold — impossible to match
    if (kotlin.math.abs(scanned.length - target.length) > threshold) return false
    return levenshtein(scanned, target) <= threshold
}