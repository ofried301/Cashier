package de.breitmeier.android.cashier.calculate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.breitmeier.android.cashier.database.productdb.Product
import de.breitmeier.android.cashier.database.productdb.ProductDatabaseDao
import kotlinx.coroutines.*

class CalculateViewModel(val selected: LiveData<MutableList<Product>>,
                         val database: ProductDatabaseDao): ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _total = MutableLiveData<Int>()
    val total: LiveData<Int>
        get() = _total

    private val _onDismissEvent = MutableLiveData<Boolean>()
    val onDismissEvent: LiveData<Boolean>
        get() = _onDismissEvent


    init {
        _total.value = 0
        calculatePrice()
    }

    private fun calculatePrice() {
        if (selected.value != null) {
            val list = selected.value
            if (list?.isNotEmpty()!!) {
                for (p in list) {
                    _total.value = _total.value?.plus(p.count * p.total)

                }
            } else {
                _total.value = 0
            }
        }
    }

    private suspend fun updateSessionCount(product: Product) {
        withContext(Dispatchers.IO) {
            product.sessionCunt += product.count
            product.count = 0
            database.update(product)
        }
    }

    private suspend fun update(product: Product) {
        withContext(Dispatchers.IO) {
            database.update(product)
        }
    }

    fun onBackClicked() {

        startOnDismissEvent()
    }

    // reset the counts of the selected elements
    fun onFinishedClicked() {
        if (selected.value != null) {
            if (selected.value!!.isNotEmpty()) {
                for (s in selected.value!!) {

                    // update product for session
                    uiScope.launch {
                        updateSessionCount(s)
                    }
                }
                _total.value = 0
            }
        }
        startOnDismissEvent()
    }

    private fun startOnDismissEvent() {
        _onDismissEvent.value = true
    }

    fun startOnDismissEventComplete() {
        _onDismissEvent.value = false
    }

}