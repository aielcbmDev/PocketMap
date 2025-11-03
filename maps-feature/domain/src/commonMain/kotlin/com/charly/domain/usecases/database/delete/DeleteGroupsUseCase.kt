package com.charly.domain.usecases.database.delete

import com.charly.domain.OpenClassForMocking
import com.charly.domain.repositories.database.delete.DeleteGroupsRepository

@OpenClassForMocking
class DeleteGroupsUseCase(
    private val deleteGroupsRepository: DeleteGroupsRepository
) {

    suspend fun execute(groupIdsSelected: Set<Long>) {
        deleteGroupsRepository.execute(groupIdsSelected)
    }
}
