package org.troikas.main.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.format.DateTimeFormatter
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
enum class Category{
    healthy,
    moderate,
    avoid
}

//this creates the actual SQLite table on the phone
@Serializable
@Entity(tableName="ingredients")
data class IngredientClassification(
    @PrimaryKey
    val name: String,
    val category: Category,
    val reason: String,
    val synonyms:List<String>=emptyList(),
    @SerialName("updated_at")
    @androidx.room.ColumnInfo(name = "updated_at") 
    val updatedAt: String?=null, // Format of timestamp: yyyy-MM-dd HH:mm:ss.SSSSSS UTC time
)