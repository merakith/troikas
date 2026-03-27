package org.troikas.main.database

import androidx.room.Database
import androidx.room.RoomDatabase

//config
@Database(
    entities=[IngredientClassification::class],  //register tables here
    version=1,  // if we add another column, we change this
    exportSchema=false  //shuts up the compiler about missing schema folders
)
abstract class AppDatabase: RoomDatabase(){
    // this abstract function tells the room to auto generate the code
    // to connect DAO queries to this specific database.
    abstract fun ingredientDao(): IngredientDao
}