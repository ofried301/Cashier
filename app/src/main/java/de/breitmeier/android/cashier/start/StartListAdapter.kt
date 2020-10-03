package de.breitmeier.android.cashier.start

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.breitmeier.android.cashier.database.productdb.Product
import de.breitmeier.android.cashier.databinding.StartListElementBinding

class StartListAdapter (private val productClickListener: ProductClickListener)
    : ListAdapter<Product, StartListAdapter.ViewHolder>(ProductDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, productClickListener)
    }


    class ViewHolder private constructor(private val binding: StartListElementBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product, productClickListener: ProductClickListener) {
            binding.product = item
            binding.productClickListener = productClickListener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = StartListElementBinding
                    .inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }


    // DiffUtil
    companion object ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.productId == newItem.productId
        }
    }


    // click Listener
    class ProductClickListener(val productClickListener: (product: Product) -> Unit) {
        fun onClick(product: Product) = productClickListener(product)
    }
}