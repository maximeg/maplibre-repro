package com.example.maplibre_repro

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform