package de.breitmeier.android.cashier.calculate

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import de.breitmeier.android.cashier.R
import de.breitmeier.android.cashier.database.productdb.Product
import de.breitmeier.android.cashier.database.productdb.ProductDatabase
import de.breitmeier.android.cashier.databinding.CalculateDialogBinding

class CalculateFragment(private val selected: LiveData<MutableList<Product>>): DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: CalculateDialogBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.calculate_dialog,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = ProductDatabase.getInstance(application).productDatabaseDao

        val viewModelFactory = CalculateViewModelFactory(selected, dataSource)

        val calculateViewModel = ViewModelProvider(this, viewModelFactory)
            .get(CalculateViewModel::class.java)

        binding.calculateViewModel = calculateViewModel
        binding.lifecycleOwner = this

        val adapter = CalculateListAdapter()

        /*calculateViewModel.total.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })*/

        calculateViewModel.selected.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        calculateViewModel.onDismissEvent.observe(viewLifecycleOwner, Observer {
            if (it) {
                dialog?.dismiss()
                calculateViewModel.startOnDismissEventComplete()
            }
        })

        binding.rvSelected.adapter = adapter

        val manager = LinearLayoutManager(context)
        binding.rvSelected.layoutManager = manager

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.95).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.60).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }

}