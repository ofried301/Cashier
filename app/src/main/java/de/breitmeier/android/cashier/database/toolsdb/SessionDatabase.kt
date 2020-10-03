package de.breitmeier.android.cashier.database.toolsdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import de.breitmeier.android.cashier.database.productdb.Product

@Database(entities = [Session::class, Product::class], version = 6, exportSchema = false)
abstract class SessionDatabase: RoomDatabase() {

    // Connects the database to the Dao
    abstract val sessionDatabaseDao: SessionDatabaseDao

    companion object {

        @Volatile
        private var  INSTANCE: SessionDatabase? = null

        fun getInstance(context: Context): SessionDatabase {

            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SessionDatabase::class.java,
                        "session_table"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }

}