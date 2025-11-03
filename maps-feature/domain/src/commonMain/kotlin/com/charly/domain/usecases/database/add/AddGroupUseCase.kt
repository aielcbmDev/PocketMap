package com.charly.domain.usecases.database.add

import com.charly.domain.OpenClassForMocking
import com.charly.domain.repositories.database.add.AddGroupRepository

@OpenClassForMocking
class AddGroupUseCase(
    private val addGroupRepository: AddGroupRepository
) {

    suspend fun execute(groupName: String) {
        addGroupRepository.execute(groupName)
    }
}
