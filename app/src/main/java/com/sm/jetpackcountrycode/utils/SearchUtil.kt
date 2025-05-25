package com.sm.jetpackcountrycode.utils

import com.sm.jetpackcountrycode.data.model.Country

object SearchUtil{
    fun filterCountries(countries: List<Country>, query: String): List<Country> {
        if (query.isEmpty()) return countries

        val lowerQuery = query.lowercase().trim()
        val filtered = countries.filter { country ->
            val nameMatches = country.name.lowercase().let { name ->
                name == lowerQuery || name.split(" ").any { it.contains(lowerQuery) }
            }
            val dialCodeMatches = country.dialCode.lowercase().contains(lowerQuery)
            nameMatches || dialCodeMatches
        }

        return filtered
    }
}