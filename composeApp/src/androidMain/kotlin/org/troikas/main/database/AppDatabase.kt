package org.troikas.main.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// config
@Database(
    entities = [IngredientClassification::class], // register tables here
    version = 1, // if we add another column, we change this
    exportSchema = false // shuts up the compiler about missing schema folders
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    // this abstract function tells the room to auto generate the code
    // to connect DAO queries to this specific database.
    abstract fun ingredientDao(): IngredientDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) { // create db if instance is null
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "troikas_database" // name of local sql file
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
