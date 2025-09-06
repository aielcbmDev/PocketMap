package com.charly.database.model.membership

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.charly.database.model.groups.GroupEntity
import com.charly.database.model.locations.LocationEntity
import kotlinx.serialization.Serializable

//  CREATE TABLE membership_table (
//      idGroup INTEGER,
//      idLocation INTEGER,
//      PRIMARY KEY (idGroup, idLocation),
//      FOREIGN KEY (idLocation) REFERENCES locations_table (_id) ON DELETE CASCADE,
//      FOREIGN KEY (idGroup) REFERENCES groups_table (_id) ON DELETE CASCADE
//  );
@Entity(
    tableName = "membership_table",
    primaryKeys = ["idGroup", "idLocation"], // Composite Primary Key
    foreignKeys = [
        ForeignKey(
            entity = GroupEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("idGroup"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LocationEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("idLocation"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )]
)
@Serializable
data class MembershipEntity(
    @ColumnInfo(index = true)
    val idGroup: Long,
    @ColumnInfo(index = true)
    val idLocation: Long,
)
