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
    return map { groupModel -> groupModel.mapToGroup() }
}

internal fun Group.mapToGroupModel(): GroupModel {
    return GroupModel(
        id = this.id,
        name = this.name
    )
}

internal fun List<Group>.mapToGroupModelList(): List<GroupModel> {
    return map { group -> group.mapToGroupModel() }
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
    return map { locationModel -> locationModel.mapToLocation() }
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
    return map { location -> location.mapToLocationModel() }
}
