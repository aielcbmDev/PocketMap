package charly.baquero.pocketmap.domain.get

import charly.baquero.pocketmap.domain.model.Group
import charly.baquero.pocketmap.domain.utils.mapToGroupList
import com.charly.database.repositories.getall.GetAllGroupsRepository

class GetAllGroupsUseCase(
    private val getAllGroupsRepository: GetAllGroupsRepository
) {

    suspend fun execute(): List<Group> {
        return getAllGroupsRepository.execute().mapToGroupList()
    }
}
