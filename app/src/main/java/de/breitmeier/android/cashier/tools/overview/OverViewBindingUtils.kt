package de.breitmeier.android.cashier.tools.overview

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat


@SuppressLint("SimpleDateFormat")
@BindingAdapter("formatMillis")
fun TextView.formatMillis(millis: Long) {
    val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm")
    text = simpleDateFormat.format(millis)
}

//@BindingAdapter("bindColor")
