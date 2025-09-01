package charly.baquero.pocketmap.data.database.model.groups

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

//  CREATE TABLE groups_table (
//      id INTEGER PRIMARY KEY AUTOINCREMENT,
//      name TEXT UNIQUE NOT NULL
//  );
@Entity(
    tableName = "groups_table",
    indices = [Index(value = ["name"], unique = true)]
)
@Serializable
data class GroupEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
)
