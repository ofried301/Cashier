package de.breitmeier.android.cashier.calculate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView;
import de.breitmeier.android.cashier.database.productdb.Product
import de.breitmeier.android.cashier.databinding.CalculateListElementBinding;

class CalculateListAdapter: ListAdapter<Product, CalculateListAdapter.ViewHolder>(ProductDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    class ViewHolder private constructor(private val binding: CalculateListElementBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product) {
            binding.product = item
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CalculateListElementBinding
                    .inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    companion object ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.productId == newItem.productId
        }

    }



}
