package charly.baquero.pocketmap.domain

import com.charly.database.model.locations.Location
import com.charly.database.repositories.getall.GetAllLocationsRepository

class GetAllLocationsUseCase(
    private val getAllLocationsRepository: GetAllLocationsRepository
) {

    suspend fun execute(): List<Location> {
        return getAllLocationsRepository.execute()
    }
}
