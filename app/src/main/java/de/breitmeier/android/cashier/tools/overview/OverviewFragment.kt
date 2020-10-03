package de.breitmeier.android.cashier.tools.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import de.breitmeier.android.cashier.R
import de.breitmeier.android.cashier.database.toolsdb.Session
import de.breitmeier.android.cashier.databinding.OverviewFragmentBinding


class OverviewFragment: Fragment() {

    private lateinit var overviewViewModel: OverviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: OverviewFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.overview_fragment,
            container,
            false
        )

        overviewViewModel = ViewModelProvider(this)
            .get(OverviewViewModel::class.java)

        val adapter = OverviewListAdapter(
            OverviewListAdapter.SessionClickListener {

            })


        binding.rvSession.adapter = adapter

        val manager = LinearLayoutManager(context)
        binding.rvSession.layoutManager = manager

        return binding.root
    }


    private fun navigateToDetailFragment(session: Session) {
        findNavController().navigate(R.id.action_overviewFragment_to_detailFragment)
    }




}





















