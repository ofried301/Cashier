package de.breitmeier.android.cashier.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.breitmeier.android.cashier.database.productdb.ProductDatabaseDao
import java.lang.IllegalArgumentException

class CreateViewModelFactory(
    private val dataSource: ProductDatabaseDao
): ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateViewModel::class.java)) {
            return CreateViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}