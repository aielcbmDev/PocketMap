package com.charly.domain.usecases.edit

import com.charly.domain.repositories.edit.EditGroupRepository

class EditGroupUseCase(
    private val editGroupRepository: EditGroupRepository
) {

    suspend fun execute(id: Long, groupName: String) {
        editGroupRepository.execute(id, groupName)
    }
}
