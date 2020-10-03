package de.breitmeier.android.cashier.database.productdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface ProductDatabaseDao {

    // insert new product
    @Insert
    fun insert(product: Product)

    // update product
    @Update
    fun update(product: Product)

    // returns Object with name == key
    // should use something else if two objects with same name where found maybe crash
    @Query("SELECT * from product_table WHERE name = :key")
    fun get(key: String): Product

    // Delete product with key = id
    @Query("DELETE FROM product_table WHERE productId = :key")
    fun delete(key: Long)

    // deletes content of database not the database itself
    @Query("DELETE FROM product_table")
    fun clear()

    // returns list of all Database elements
    @Query("SELECT * FROM product_table ORDER BY productId DESC")
    fun getAllProduct(): LiveData<MutableList<Product>>

    // select and return the latest product
    @Query("SELECT * FROM product_table ORDER BY productId DESC LIMIT 1")
    fun getLatestProduct(): Product?

    // select product by id
    @Query("SELECT * from product_table WHERE productId = :key")
    fun getProductWithId(key: Long): LiveData<Product>

}














