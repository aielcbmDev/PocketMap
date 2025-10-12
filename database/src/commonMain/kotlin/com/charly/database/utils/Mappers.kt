package com.charly.database.utils

import com.charly.database.model.groups.GroupEntity
import com.charly.database.model.locations.LocationEntity
import com.charly.domain.model.Group
import com.charly.domain.model.Location

internal fun GroupEntity.mapToGroup(): Group {
    return Group(
        id = this.id,
        name = this.name
    )
}

internal fun List<GroupEntity>.mapToGroupList(): List<Group> {
    return map { groupEntity -> groupEntity.mapToGroup() }
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
    return map { group -> group.mapToGroupEntity() }
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
    return map { locationEntity -> locationEntity.mapToLocation() }
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
    return map { location -> location.mapToLocationEntity() }
}
