package de.breitmeier.android.cashier

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import androidx.databinding.BindingAdapter
import de.breitmeier.android.cashier.database.productdb.Product

private const val TAG = "Utils.kt"

@BindingAdapter("bindImage")
fun bindImage(imgView: ImageView, product: Product) {
    if (product.hasImage) {
        product.imgUri.let {
            Glide.with(imgView.context)
                .load(product.imgUri)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                )
                .into(imgView)
        }
    }
}























