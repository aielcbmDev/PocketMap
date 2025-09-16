package charly.baquero.pocketmap.domain

import charly.baquero.pocketmap.domain.model.Location
import charly.baquero.pocketmap.domain.utils.mapToLocationList
import com.charly.database.repositories.getall.GetAllLocationsRepository

class GetAllLocationsUseCase(
    private val getAllLocationsRepository: GetAllLocationsRepository
) {

    suspend fun execute(): List<Location> {
        return getAllLocationsRepository.execute().mapToLocationList()
    }
}
