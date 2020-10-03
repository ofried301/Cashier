package de.breitmeier.android.cashier.tools.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.breitmeier.android.cashier.database.toolsdb.Session
import de.breitmeier.android.cashier.databinding.OverviewListElementBinding

class OverviewListAdapter (private val sessionClickListener: SessionClickListener)
    : ListAdapter<Session, OverviewListAdapter.ViewHolder>(ProductDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, sessionClickListener)
    }


    class ViewHolder private constructor(private val binding: OverviewListElementBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Session, sessionClickListener: SessionClickListener) {
            binding.session = item
            binding.sessionClickListener = sessionClickListener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = OverviewListElementBinding
                    .inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }


    // DiffUtil
    companion object ProductDiffCallback : DiffUtil.ItemCallback<Session>() {
        override fun areItemsTheSame(oldItem: Session, newItem: Session): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Session, newItem: Session): Boolean {
            return oldItem.sessionId == newItem.sessionId
        }
    }


    // click Listener
    class SessionClickListener(val sessionClickListener: (session: Session) -> Unit) {
        fun onClick(session: Session) = sessionClickListener(session)
    }
}