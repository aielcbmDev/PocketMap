package com.charly.core.mappers.database

import com.charly.database.model.groups.GroupEntity
import com.charly.database.model.locations.LocationEntity
import com.charly.domain.model.database.Group
import com.charly.domain.model.database.Location

internal fun GroupEntity.mapToGroup(): Group {
    return Group(
        id = this.id,
        name = this.name
    )
}

internal fun List<GroupEntity>.mapToGroupList(): List<Group> {
    return map { it.mapToGroup() }
}

internal fun String.mapToGroupEntity(): GroupEntity {
    return GroupEntity(
        name = this
    )
}

internal fun Group.mapToGroupEntity(): GroupEntity {
    return GroupEntity(
        id = this.id,
        name = this.name
    )
}

internal fun List<Group>.mapToGroupEntityList(): List<GroupEntity> {
    return map { it.mapToGroupEntity() }
}

internal fun LocationEntity.mapToLocation(): Location {
    return Location(
        id = this.id,
        title = this.title,
        description = this.description,
        latitude = this.latitude,
        longitude = this.longitude
    )
}

internal fun List<LocationEntity>.mapToLocationList(): List<Location> {
    return map { it.mapToLocation() }
}

internal fun Location.mapToLocationEntity(): LocationEntity {
    return LocationEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        latitude = this.latitude,
        longitude = this.longitude
    )
}

internal fun List<Location>.mapToLocationEntityList(): List<LocationEntity> {
    return map { it.mapToLocationEntity() }
}
