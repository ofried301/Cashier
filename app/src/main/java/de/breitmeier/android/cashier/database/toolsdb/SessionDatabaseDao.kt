package de.breitmeier.android.cashier.database.toolsdb

import androidx.lifecycle.LiveData
import androidx.room.*
import de.breitmeier.android.cashier.database.productdb.Product

@Dao
interface SessionDatabaseDao {

    @Insert
    fun insert(session: Session)

    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProductList(dataList:ArrayList<Product>)*/

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(session: Session)

    @Query("SELECT * FROM session_table WHERE sessionId = :key")
    fun get(key: Long): Session

    @Query("SELECT * FROM session_table WHERE name = :key")
    fun get(key: String): Session

    @Query("DELETE FROM session_table")
    fun clear()

    @Query("SELECT * FROM session_table ORDER BY sessionId DESC")
    fun getAllSessions(): LiveData<MutableList<Session>>

    @Query("SELECT * from session_table WHERE sessionId = :key")
    fun getSessionWithId(key: Long): LiveData<Session>
}