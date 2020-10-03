package de.breitmeier.android.cashier.calculate

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import de.breitmeier.android.cashier.database.productdb.Product

@SuppressLint("SetTextI18n")
@BindingAdapter("createCalculateString")
fun TextView.createCalculateString(product: Product?) {
    product?.let {
        text = "${product.count}x ${product.name}"
    }
}


@SuppressLint("SetTextI18n")
@BindingAdapter("formatTotal")
fun TextView.formatTotal(total: Int) {
    val x: Double = total.toDouble()/100
    text = "%.2f".format(x) +"€"
}