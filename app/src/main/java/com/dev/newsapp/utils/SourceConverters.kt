package com.dev.newsapp.utils

import androidx.room.TypeConverter
import com.dev.newsapp.data.model.Source
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SourceConverters {
    private val gson = Gson()
    @TypeConverter
    fun fromSource(source: Source): String{
        return gson.toJson(source)
    }

    @TypeConverter
    fun toSource(sourceString: String): Source{
        val type = object : TypeToken<Source>() {}.type
        return gson.fromJson(sourceString,type)
    }
}