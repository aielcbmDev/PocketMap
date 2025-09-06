package com.charly.database.model.locations

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

//  CREATE TABLE locations_table (
//      id INTEGER PRIMARY KEY AUTOINCREMENT,
//      title TEXT UNIQUE NOT NULL
//      description TEXT
//      latitude REAL NOT NULL,
//      longitude REAL NOT NULL,
//  );
@Entity(
    tableName = "locations_table",
    indices = [Index(value = ["title"], unique = true)]
)
@Serializable
data class LocationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String? = null,
    val latitude: Float,
    val longitude: Float,
)
