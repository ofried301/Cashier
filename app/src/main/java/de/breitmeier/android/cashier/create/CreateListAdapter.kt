package de.breitmeier.android.cashier.create

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.breitmeier.android.cashier.database.productdb.Product
import de.breitmeier.android.cashier.databinding.CreateListElementBinding

class CreateListAdapter (private val deleteClickListener: DeleteClickListener,
                         private val selectPictureClickListener: SelectPictureClickListener)
    : ListAdapter<Product, CreateListAdapter.ViewHolder>(ProductDiffCallback) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, deleteClickListener, selectPictureClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: CreateListElementBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product, deleteClickListener: DeleteClickListener,
                 selectPictureClickListener: SelectPictureClickListener) {
            binding.product = item
            binding.deleteClickListener = deleteClickListener
            binding.selectPictureClickListener = selectPictureClickListener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CreateListElementBinding
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

    class DeleteClickListener(val deleteClickListener: (product: Product) -> Unit) {
        fun onClick(product: Product) = deleteClickListener(product)
    }

    class SelectPictureClickListener(val selectPictureClickListener: (product: Product) -> Unit) {
        fun onClick(product: Product) = selectPictureClickListener(product)
    }
}