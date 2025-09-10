package com.charly.database.utils

import com.charly.database.model.groups.Group
import com.charly.database.model.groups.GroupEntity
import com.charly.database.model.locations.Location
import com.charly.database.model.locations.LocationEntity
import com.charly.database.model.membership.Membership
import com.charly.database.model.membership.MembershipEntity

internal fun GroupEntity.mapToGroup(): Group {
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

internal fun Membership.mapToMembershipEntity(): MembershipEntity {
    return MembershipEntity(
        idGroup = this.idGroup,
        idLocation = this.idLocation
    )
}

internal fun List<Membership>.mapToMembershipEntityList(): List<MembershipEntity> {
    val list = mutableListOf<MembershipEntity>()
    forEach { membership ->
        val membershipEntity = membership.mapToMembershipEntity()
        list.add(membershipEntity)
    }
    return list
}
