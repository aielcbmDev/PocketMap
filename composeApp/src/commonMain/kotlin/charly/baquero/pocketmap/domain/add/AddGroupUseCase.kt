package charly.baquero.pocketmap.domain.add

import charly.baquero.pocketmap.domain.utils.mapToGroupEntity
import com.charly.database.repositories.add.AddGroupRepository

class AddGroupUseCase(
    private val addGroupRepository: AddGroupRepository
) {

    suspend fun execute(groupName: String) {
        addGroupRepository.execute(groupName.mapToGroupEntity())
    }
}
