package de.breitmeier.android.cashier.tools.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OverviewViewModel: ViewModel() {

    private val _navigateToCreateSessionEvent = MutableLiveData<Boolean>()
    val navigateToCreateSession: LiveData<Boolean>
        get() = _navigateToCreateSessionEvent

    /*private val _navigateToDetailSessionEvent = MutableLiveData<Boolean>()
    val navigateToDetailSessionEvent: LiveData<Boolean>
        get() = _navigateToDetailSessionEvent*/

    init {
        _navigateToCreateSessionEvent.value = false
    }

    fun onClickCreateSession () {

        navigateToCreateSessionStarted()
    }


    private fun navigateToCreateSessionStarted() {
        _navigateToCreateSessionEvent.value = true
    }

    fun navigateToCreateSessionCompleted() {
        _navigateToCreateSessionEvent.value = false
    }

    /*fun navigateToDetailSessionStarted() {
        _navigateToDetailSessionEvent.value = true
    }

    private fun navigateToDetailSessionCompleted() {
        _navigateToDetailSessionEvent.value = false
    }*/

}