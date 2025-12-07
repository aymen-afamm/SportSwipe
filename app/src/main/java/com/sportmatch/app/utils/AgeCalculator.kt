package com.sportmatch.app.utils

import java.util.Calendar
import java.util.Date

object AgeCalculator {
    fun calculateAge(dateOfBirth: Date): Int {
        val today = Calendar.getInstance()
        val birthDate = Calendar.getInstance().apply {
            time = dateOfBirth
        }
        var age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)
        val monthDiff = today.get(Calendar.MONTH) - birthDate.get(Calendar.MONTH)
        if (monthDiff < 0 || (monthDiff == 0 && today.get(Calendar.DAY_OF_MONTH) < birthDate.get(Calendar.DAY_OF_MONTH))) {
            age--
        }
        return age
    }

    fun isAdult(dateOfBirth: Date): Boolean {
        return calculateAge(dateOfBirth) >= 18
    }
}

