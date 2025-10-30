package com.charly.domain.usecases.add

import com.charly.domain.OpenClassForMocking
import com.charly.domain.repositories.add.AddGroupRepository

@OpenClassForMocking
class AddGroupUseCase(
    private val addGroupRepository: AddGroupRepository
) {

    suspend fun execute(groupName: String) {
        addGroupRepository.execute(groupName)
    }
}
