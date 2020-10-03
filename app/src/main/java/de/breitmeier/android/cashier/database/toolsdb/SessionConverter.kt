package de.breitmeier.android.cashier.database.toolsdb

import androidx.room.TypeConverter
import com.google.gson.Gson
import de.breitmeier.android.cashier.database.productdb.Product

// converts list of ProductIds to a usable form
// for the room database

class SessionConverter {

    @TypeConverter
    fun listToJson(value: List<Product>?): String? {
        value?.let {
            return  Gson().toJson(value)

        }?: return null
    }

    @TypeConverter
    fun jsonToList(value: String?): List<Product>? {
        val objects = Gson().fromJson(value, Array<Product>::class.java)
        return objects?.toList()
    }

}