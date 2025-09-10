package charly.baquero.pocketmap.domain

import com.charly.database.model.groups.Group
import com.charly.database.repositories.getall.GetAllGroupsRepository

class GetAllGroupsUseCase(
    private val getAllGroupsRepository: GetAllGroupsRepository
) {

    suspend fun execute(): List<Group> {
        return getAllGroupsRepository.execute()
    }
}
