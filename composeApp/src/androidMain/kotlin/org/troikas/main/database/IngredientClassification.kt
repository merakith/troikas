package org.troikas.main.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.format.DateTimeFormatter

enum class Category{
    HEALTHY,
    MODERATE,
    AVOID
}

//this creates the actual SQLite table on the phone
@Entity(tableName="ingredient_classification")
data class IngredientClassification(
    @PrimaryKey
    val name: String,
    val category: Category,
    val reason: String,
    val updatedAt: String, // Format of timestamp: yyyy-MM-dd HH:mm:ss.SSSSSS UTC time
)