package de.breitmeier.android.cashier.start

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.breitmeier.android.cashier.database.productdb.Product
import de.breitmeier.android.cashier.database.productdb.ProductDatabaseDao

class StartViewModel(val database: ProductDatabaseDao): ViewModel() {

    private val TAG = "StartViewModel"

    private val _products = database.getAllProduct()
    val products: LiveData<MutableList<Product>>
        get() = _products

    private var _selected = MutableLiveData<MutableList<Product>>()
    val selected: LiveData<MutableList<Product>>
        get() = _selected


    private val _startDialogEvent = MutableLiveData<Boolean>()
    val startDialogEvent: LiveData<Boolean>
        get() = _startDialogEvent

    private val _startCreateTableEvent = MutableLiveData<Boolean>()
    val startCreateTableEvent: LiveData<Boolean>
        get() = _startCreateTableEvent

    init {
        Log.d(TAG, "StartViewModel will be created")
        // reset count of every product to start at 0
        _startCreateTableEvent.value = false
        _startDialogEvent.value = false
        onClearCountClicked()
        Log.d(TAG, "init StartViewModel finished")
    }

    fun onClearCountClicked() {
        if (_products.value != null) {
            for (product in _products.value!!) {
                product.count = 0
            }
        }
    }

    fun increaseCount(product: Product) {
        product.count++
    }

    fun onNextClicked() {
        val tempSelected = mutableListOf<Product>()
        // get selected products
        if (_products.value != null) {
            // add every selected product to list
            for (p in _products.value!!) {
                if (p.count != 0) {
                    tempSelected.add(p)
                }
            }
            _selected.value = tempSelected
        }
        // inform fragment btn_next was clicked
        onStartDialogEvent()
    }

    fun onChangeTableClicked() {
        Log.d(TAG, "onChangeTableClicked")
        onStartChangeTableEvent()
    }

    private fun onStartChangeTableEvent() {
        _startCreateTableEvent.value = true
    }

    fun onStartChangeTableEventComplete() {
        _startCreateTableEvent.value = false
    }

    private fun onStartDialogEvent() {
        _startDialogEvent.value = true
    }

    fun onStartDialogEventComplete() {
        _startDialogEvent.value = false
    }

    override fun onCleared() {
        super.onCleared()

    }

}