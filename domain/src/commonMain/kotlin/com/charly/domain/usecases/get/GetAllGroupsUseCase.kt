package com.charly.domain.usecases.get

import com.charly.domain.model.Group
import com.charly.domain.repositories.get.GetAllGroupsRepository

class GetAllGroupsUseCase(
    private val getAllGroupsRepository: GetAllGroupsRepository
) {

    suspend fun execute(): List<Group> {
        return getAllGroupsRepository.execute()
    }
}
