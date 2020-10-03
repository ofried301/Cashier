package de.breitmeier.android.cashier.start

import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import de.breitmeier.android.cashier.database.productdb.Product


@BindingAdapter("createCountString")
fun TextView.createCountString(product: Product?) {
    product?.let {
        text = product.count.toString()
        Log.d("BindingUtils", "count should be displayed")
    }
}



















