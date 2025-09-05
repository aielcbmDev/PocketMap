package charly.baquero.pocketmap.utils

import charly.baquero.pocketmap.data.database.model.groups.GroupEntity
import charly.baquero.pocketmap.data.database.model.locations.LocationEntity
import charly.baquero.pocketmap.data.database.model.membership.MembershipEntity
import kotlinx.serialization.json.Json
import pocketmap.composeapp.generated.resources.Res

class AssetFileProvider {

    companion object {
        private const val LOCATIONS_FILE_PATH: String = "files/locations.json"
        private const val GROUPS_FILE_PATH: String = "files/groups.json"
        private const val MEMBERSHIPS_FILE_PATH: String = "files/memberships.json"
    }

    private suspend fun readJsonFile(filePath: String): String {
        val byteArray = Res.readBytes(filePath)
        return byteArray.decodeToString()
    }

    suspend fun getLocationEntityList(filePath: String = LOCATIONS_FILE_PATH): List<LocationEntity> {
        val jsonDataList = readJsonFile(filePath)
        return Json.decodeFromString<List<LocationEntity>>(jsonDataList)
    }

    suspend fun getGroupEntityList(filePath: String = GROUPS_FILE_PATH): List<GroupEntity> {
        val jsonDataList = readJsonFile(filePath)
        return Json.decodeFromString<List<GroupEntity>>(jsonDataList)
    }

    suspend fun getMembershipEntityList(filePath: String = MEMBERSHIPS_FILE_PATH): List<MembershipEntity> {
        val jsonDataList = readJsonFile(filePath)
        return Json.decodeFromString<List<MembershipEntity>>(jsonDataList)
    }
}
