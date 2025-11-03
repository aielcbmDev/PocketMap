package com.charly.domain.usecases.database.get

import com.charly.domain.OpenClassForMocking
import com.charly.domain.model.database.Group
import com.charly.domain.repositories.database.get.GetAllGroupsRepository
import kotlinx.coroutines.flow.Flow

@OpenClassForMocking
class GetAllGroupsUseCase(
    private val getAllGroupsRepository: GetAllGroupsRepository
) {

    suspend fun execute(): Flow<List<Group>> {
        return getAllGroupsRepository.execute()
    }
}
