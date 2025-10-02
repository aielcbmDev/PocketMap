package charly.baquero.pocketmap.ui.utils

import charly.baquero.pocketmap.ui.model.GroupModel
import charly.baquero.pocketmap.ui.model.LocationModel
import com.charly.domain.model.Group
import com.charly.domain.model.Location

internal fun GroupModel.mapToGroup(): Group {
    return Group(
        id = this.id,
        name = this.name
    )
}

internal fun List<GroupModel>.mapToGroupList(): List<Group> {
    val list = mutableListOf<Group>()
    forEach { groupModel ->
        val group = groupModel.mapToGroup()
        list.add(group)
    }
    return list
}

internal fun Group.mapToGroupModel(): GroupModel {
    return GroupModel(
        id = this.id,
        name = this.name
    )
}

internal fun List<Group>.mapToGroupModelList(): List<GroupModel> {
    val list = mutableListOf<GroupModel>()
    forEach { group ->
        val groupModel = group.mapToGroupModel()
        list.add(groupModel)
    }
    return list
}

internal fun LocationModel.mapToLocation(): Location {
    return Location(
        id = this.id,
        title = this.title,
        description = this.description,
        latitude = this.latitude,
        longitude = this.longitude
    )
}

internal fun List<LocationModel>.mapToLocationList(): List<Location> {
    val list = mutableListOf<Location>()
    forEach { locationModel ->
        val location = locationModel.mapToLocation()
        list.add(location)
    }
    return list
}

internal fun Location.mapToLocationModel(): LocationModel {
    return LocationModel(
        id = this.id,
        title = this.title,
        description = this.description,
        latitude = this.latitude,
        longitude = this.longitude
    )
}

internal fun List<Location>.mapToLocationModelList(): List<LocationModel> {
    val list = mutableListOf<LocationModel>()
    forEach { location ->
        val locationModel = location.mapToLocationModel()
        list.add(locationModel)
    }
    return list
}
