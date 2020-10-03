package de.breitmeier.android.cashier.database.productdb

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "product_table")
data class Product (
    @PrimaryKey(autoGenerate = true)
    var productId: Long = 0L,

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "price")
    var price: Int = 0,

    @ColumnInfo(name = "deposit")
    var deposit: Int = 0,

    @ColumnInfo(name = "total")
    var total: Int = 0,

    @ColumnInfo(name = "position")
    var position: Int = -1,

    @ColumnInfo(name = "imgUri")
    var imgUri: String = "",

    // should declare new variable: hasImage: Boolean
    // to make sure only right images where load into the list

    @ColumnInfo(name = "hasImage")
    var hasImage: Boolean = false,

    @ColumnInfo(name = "session_count")
    var sessionCunt: Int = 0

) : BaseObservable() {

    init {
        total = price + deposit
    }

    fun setTotal(price: Int, deposit: Int) {
        total = price + deposit
    }

    @get:Bindable
    var count: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.count)
        }
}
