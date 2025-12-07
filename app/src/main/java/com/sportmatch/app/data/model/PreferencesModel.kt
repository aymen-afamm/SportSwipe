package com.sportmatch.app.data.model

data class PreferencesModel(
    val gender: String = "",
    val distanceMax: Int = 50
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "gender" to gender,
            "distanceMax" to distanceMax
        )
    }
}

