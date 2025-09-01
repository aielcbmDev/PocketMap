package charly.baquero.pocketmap.utils

import kotlinx.serialization.json.Json
import pocketmap.composeapp.generated.resources.Res

class AssetFileProvider() {

    private suspend fun readJsonFile(filePath: String): String {
        val byteArray = Res.readBytes(filePath)
        return byteArray.decodeToString()
    }

    suspend fun <T> getDataList(filePath: String): List<T> {
        val jsonDataList = readJsonFile(filePath)
        return Json.decodeFromString<List<T>>(jsonDataList)
    }
}
