package org.troikas.main

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform