package de.breitmeier.android.cashier.database.toolsdb

import androidx.room.*
import de.breitmeier.android.cashier.database.productdb.Product

@Entity(tableName = "session_table")
class Session (

    @PrimaryKey(autoGenerate = true)
    var sessionId: Long = 0L,

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "isActive")
    var isActive: Boolean = false,

    @ColumnInfo(name = "start_time_millis")
    var startTimeMilli: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "end_time_millis")
    var endTimeMillis: Long = startTimeMilli,

    @ColumnInfo(name = "revenue")
    var revenue: Long = 0L

) {
    @TypeConverters(SessionConverter::class)
    @ColumnInfo(name = "products")
    var products: MutableList<Product>? = null

}