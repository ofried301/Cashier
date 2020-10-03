package de.breitmeier.android.cashier.calculate

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.breitmeier.android.cashier.database.productdb.Product
import de.breitmeier.android.cashier.database.productdb.ProductDatabaseDao
import java.lang.IllegalArgumentException

class CalculateViewModelFactory(
    private val selected: LiveData<MutableList<Product>>,
    private val dataSource: ProductDatabaseDao

) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalculateViewModel::class.java)) {
            return CalculateViewModel(selected, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}