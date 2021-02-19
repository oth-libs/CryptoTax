package io.cryptotax

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Utils {

    fun fromObject(data: MutableList<Transaction>): String {
        return Gson().toJson(data, object : TypeToken<MutableList<Transaction>>() {}.type)
    }

    fun toObject(string: String): MutableList<Transaction> {
        return Gson().fromJson<MutableList<Transaction>>(
            string, object : TypeToken<MutableList<Transaction>>() {}.type
        )
    }
}