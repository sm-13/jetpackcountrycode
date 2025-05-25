package com.sm.jetpackcountrycode.utils

import android.content.Context
import android.util.Log
import com.sm.jetpackcountrycode.data.model.Country
import kotlinx.serialization.json.Json


val json = Json {
    ignoreUnknownKeys = true
}


object JsonUtils {
    fun loadCountryListFromAssets(context: Context,fileName: String = "countries.json"): List<Country>{
        return try {
            val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            json.decodeFromString(jsonString)
        }catch (e: Exception){
            Log.e("Json Util","${e.cause} ---- ${e.message}")
            emptyList()
        }
    }
}