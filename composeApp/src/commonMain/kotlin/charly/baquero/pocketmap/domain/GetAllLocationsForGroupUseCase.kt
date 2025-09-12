package charly.baquero.pocketmap.domain

import com.charly.database.model.locations.Location
import com.charly.database.repositories.getall.GetAllLocationsForGroupRepository

class GetAllLocationsForGroupUseCase(
    private val getAllLocationsForGroupRepository: GetAllLocationsForGroupRepository
) {

    suspend fun execute(idGroup: Long): List<Location> {
        return getAllLocationsForGroupRepository.execute(idGroup)
    }
}
