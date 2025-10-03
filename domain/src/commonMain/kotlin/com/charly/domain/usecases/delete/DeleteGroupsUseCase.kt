package com.charly.domain.usecases.delete

import com.charly.domain.OpenClassForMocking
import com.charly.domain.repositories.delete.DeleteGroupsRepository

@OpenClassForMocking
class DeleteGroupsUseCase(
    private val deleteGroupsRepository: DeleteGroupsRepository
) {

    suspend fun execute(groupIdsSelected: Set<Long>) {
        deleteGroupsRepository.execute(groupIdsSelected)
    }
}
