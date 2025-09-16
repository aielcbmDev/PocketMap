package charly.baquero.pocketmap.domain.utils

import charly.baquero.pocketmap.domain.model.Group
import charly.baquero.pocketmap.domain.model.Location
import com.charly.database.model.groups.GroupEntity
import com.charly.database.model.locations.LocationEntity

fun GroupEntity.mapToGroup(): Group {
    return Group(
        id = this.id,
        name = this.name
    )
}

internal fun List<GroupEntity>.mapToGroupList(): List<Group> {
    val list = mutableListOf<Group>()
    forEach { groupEntity ->
        val group = groupEntity.mapToGroup()
        list.add(group)
    }
    return list
}

internal fun Group.mapToGroupEntity(): GroupEntity {
    return GroupEntity(
        id = this.id,
        name = this.name
    )
}

internal fun List<Group>.mapToGroupEntityList(): List<GroupEntity> {
    val list = mutableListOf<GroupEntity>()
    forEach { group ->
        val groupEntity = group.mapToGroupEntity()
        list.add(groupEntity)
    }
    return list
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
    val list = mutableListOf<Location>()
    forEach { locationEntity ->
        val location = locationEntity.mapToLocation()
        list.add(location)
    }
    return list
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
    val list = mutableListOf<LocationEntity>()
    forEach { location ->
        val locationEntity = location.mapToLocationEntity()
        list.add(locationEntity)
    }
    return list
}