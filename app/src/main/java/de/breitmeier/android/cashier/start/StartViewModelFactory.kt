package de.breitmeier.android.cashier.start

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.breitmeier.android.cashier.database.productdb.ProductDatabaseDao
import java.lang.IllegalArgumentException

class StartViewModelFactory(
    private val dataSource: ProductDatabaseDao
) : ViewModelProvider.Factory {

    private val TAG = "StartViewModelFactory"

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        Log.d(TAG, "StartViewModelFactory creates class")
        if(modelClass.isAssignableFrom(StartViewModel::class.java)) {
            return StartViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}