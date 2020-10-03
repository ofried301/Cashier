package de.breitmeier.android.cashier.database.productdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Product::class], version = 6, exportSchema = false)
abstract class ProductDatabase: RoomDatabase() {

    // Connects the database to the Dao
    abstract val productDatabaseDao: ProductDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: ProductDatabase? = null

        fun getInstance(context: Context): ProductDatabase {

            // Multiple threads can ask for the database at the same time
            // with using synchronized it is save that the database is only once initialized
            synchronized(this) {
                var instance =
                    INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ProductDatabase::class.java,
                        "product_table"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    // Assign INSTANCE to the newly created database.
                    INSTANCE = instance
                }

                // Return instance
                return instance
            }
        }
    }
}

























