package com.arrazyfathan.nytimes.data.local

import androidx.room.TypeConverter
import com.arrazyfathan.nytimes.data.model.Multimedia
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    companion object {

        @JvmStatic
        @TypeConverter
        fun toListMultimedia(string: String?): List<Multimedia>? {
            val listType = object : TypeToken<List<Multimedia>>(){}.type
            return if (string != null) {
                Gson().fromJson<List<Multimedia>>(string, listType)
            } else {
                null
            }
        }


        @JvmStatic
        @TypeConverter
        fun fromListMultimedia(list: List<Multimedia>?): String? {
            val type = object : TypeToken<List<Multimedia>>(){}.type
            return if (list != null) {
                Gson().toJson(list, type)
            } else {
                null
            }
        }

    }

}