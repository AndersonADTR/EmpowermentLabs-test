package com.example.empowermentlabs.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object StringTypeConverter {

    @TypeConverter
    @JvmStatic
    fun stringToListString(value: String): List<String> {
        return Gson().fromJson(value, object : TypeToken<List<String>>() {}.type)
    }

    @TypeConverter
    @JvmStatic
    fun listStringToString(value: List<String>?): String {
        return if (value == null) "" else Gson().toJson(value)
    }
}