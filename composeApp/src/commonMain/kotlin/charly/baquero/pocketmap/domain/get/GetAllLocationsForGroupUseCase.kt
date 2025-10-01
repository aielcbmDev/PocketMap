package charly.baquero.pocketmap.domain.get

import charly.baquero.pocketmap.domain.model.Location
import charly.baquero.pocketmap.domain.utils.mapToLocationList
import com.charly.database.repositories.getall.GetAllLocationsForGroupRepository

class GetAllLocationsForGroupUseCase(
    private val getAllLocationsForGroupRepository: GetAllLocationsForGroupRepository
) {

    suspend fun execute(idGroup: Long): List<Location> {
        return getAllLocationsForGroupRepository.execute(idGroup).mapToLocationList()
    }
}
