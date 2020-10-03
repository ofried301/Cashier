package de.breitmeier.android.cashier.start

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import de.breitmeier.android.cashier.R
import de.breitmeier.android.cashier.calculate.CalculateFragment
import de.breitmeier.android.cashier.database.productdb.ProductDatabase
import de.breitmeier.android.cashier.databinding.StartFragmentBinding


class StartFragment: Fragment() {

    private val TAG = "StartFragment"

    private lateinit var startViewModel: StartViewModel

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: StartFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.start_fragment,
            container,
            false
        )

        Log.d(TAG, "onCreateView StartFragment started")
        // create and get instance of Database
        val application = requireNotNull(this.activity).application
        val dataSource = ProductDatabase.getInstance(application).productDatabaseDao

        // declare Factory
        val viewModelFactory = StartViewModelFactory(dataSource)

        // initialize ViewModel
        startViewModel = ViewModelProvider(this, viewModelFactory)
            .get(StartViewModel::class.java)

        binding.startViewModel = startViewModel
        binding.lifecycleOwner = this

        // Adapter with ClickListener
        val adapter = StartListAdapter(
            StartListAdapter.ProductClickListener {
                //it.count = it.count+1
                startViewModel.increaseCount(it)
            })

        // listen for changes in products list
        startViewModel.products.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        // When Observer true show dialog
        startViewModel.startDialogEvent.observe(viewLifecycleOwner, Observer {startDialogEvent ->
            if (startDialogEvent) showDialog()
        })

        // When Observer true navigate to CreateFragment to make changes at the Database and Products
        startViewModel.startCreateTableEvent.observe(viewLifecycleOwner, Observer { startCreateTableEvent ->
            if (startCreateTableEvent) navigateToCreateTable()
        })


        binding.rvProducts.adapter = adapter

        val manager = LinearLayoutManager(context)
        binding.rvProducts.layoutManager = manager

        Log.d(TAG, "onCreateView StartFragment finished")
        return binding.root
    }


    private fun navigateToCreateTable() {
        Log.d(TAG, "Navigate to create Table")
        findNavController().navigate(R.id.action_startFragment_to_createFragment)
        startViewModel.onStartChangeTableEventComplete()
    }


    private fun showDialog() {
        activity?.supportFragmentManager?.let {
            CalculateFragment(startViewModel.selected).show(it, "CalculateDialog")
        }
        // inform ViewModel about performed action
        startViewModel.onStartDialogEventComplete()

    }



    override fun onStart() {
        super.onStart()
        // could set with of elements to 10% of whole display size, or another percentage
        //val height = (resources.displayMetrics.widthPixels * 0.1).toInt()
    }

}

















