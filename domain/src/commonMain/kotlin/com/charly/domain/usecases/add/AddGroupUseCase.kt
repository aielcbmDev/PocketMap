package com.charly.domain.usecases.add

import com.charly.domain.repositories.add.AddGroupRepository

class AddGroupUseCase(
    private val addGroupRepository: AddGroupRepository
) {

    suspend fun execute(groupName: String) {
        addGroupRepository.execute(groupName)
    }
}
