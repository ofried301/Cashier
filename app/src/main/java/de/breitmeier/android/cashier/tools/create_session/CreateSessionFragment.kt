package de.breitmeier.android.cashier.tools.create_session

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import de.breitmeier.android.cashier.R
import de.breitmeier.android.cashier.databinding.CreateSessionFragmentBinding

class CreateSessionFragment: Fragment() {

    private lateinit var createSessionViewModel: CreateSessionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: CreateSessionFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.create_session_fragment,
            container,
            false
        )

        createSessionViewModel = ViewModelProvider(this)
            .get(CreateSessionViewModel::class.java)




        return binding.root
    }

}


















