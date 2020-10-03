package de.breitmeier.android.cashier.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.breitmeier.android.cashier.database.productdb.Product
import de.breitmeier.android.cashier.database.productdb.ProductDatabaseDao
import kotlinx.coroutines.*

class CreateViewModel(val database: ProductDatabaseDao): ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val product = MutableLiveData<Product>()

    val name = MutableLiveData<String>()
    val price = MutableLiveData<String>()
    val deposit = MutableLiveData<String>()
    val isDeposit = MutableLiveData<Boolean>()


    private val _products = database.getAllProduct()
    val products: LiveData<MutableList<Product>>
        get() = _products

    init {
        name.value = ""
        price.value = null
        deposit.value = null
        isDeposit.value = false
    }

    fun onAddClicked(name: String, price: String, deposit: String, isDeposit: Boolean) {
        uiScope.launch {
            withContext(Dispatchers.Default) {
                val product = Product()
                product.name = name

                if(isDeposit) {
                    product.price = 0
                    product.deposit = (price.toDouble() *-100).toInt()

                } else {
                    product.price = (price.toDouble() *100).toInt()
                    product.deposit = (deposit.toDouble() *100).toInt()
                }
                product.setTotal(product.price, product.deposit)
                product.position = _products.value?.size?.plus(1) ?: -1
                product.hasImage = false

                uiScope.launch {
                    insert(product)
                }
            }
        }
    }

    fun updateProductImg(product: Product) {
        uiScope.launch {
            product.hasImage = true
            update(product)
        }
    }

    fun onDeleteClicked(product: Product) {
        uiScope.launch {
            deleteProduct(product)
        }
    }

    private suspend fun update(product: Product) {
        withContext(Dispatchers.IO) {
            database.update(product)
        }
    }

    private suspend fun insert(product: Product) {
        withContext(Dispatchers.IO) {
            database.insert(product)
        }
    }

    private suspend fun deleteProduct(product: Product) {
        withContext(Dispatchers.IO) {
            database.delete(product.productId)
        }
    }

}






















