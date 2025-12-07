package com.sportmatch.app.data.model

data class LocationModel(
    val lat: Double = 0.0,
    val lon: Double = 0.0
) {
    fun toMap(): Map<String, Double> {
        return mapOf(
            "lat" to lat,
            "lon" to lon
        )
    }
}

