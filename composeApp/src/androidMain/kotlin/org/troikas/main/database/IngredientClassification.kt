package org.troikas.main.database

import androidx.room.Entity
import androidx.room.PrimaryKey

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
    val description:String?
)