package de.breitmeier.android.cashier.create

import android.annotation.SuppressLint
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import de.breitmeier.android.cashier.database.productdb.Product

// for the create Table layout to make 'Price: 4€' as string and with data binding
@SuppressLint("SetTextI18n")
@BindingAdapter("createPriceString")
fun TextView.createPriceString(product: Product?) {
    product?.let {
        val price: Double = product.price.toDouble()/100
        text = "Price: " + "%.2f".format(price) +"€"
    }
}


// for the create Table layout to make 'Deposit: 2€' as string and with data binding
@SuppressLint("SetTextI18n")
@BindingAdapter("createDepositString")
fun TextView.createDepositString(product: Product?) {
    product?.let {
        val deposit: Double = product.deposit.toDouble()/100
        text = "Deposit: " + "%.2f".format(deposit) +"€"
    }
}


@BindingAdapter("setPrice")
fun EditText.setPrice(price: String): Int {
    return (price.toDouble() *100).toInt()
}

@BindingAdapter("setDeposit")
fun EditText.setDeposit(deposit: String): Int {
    return (deposit.toDouble() *100).toInt()
}