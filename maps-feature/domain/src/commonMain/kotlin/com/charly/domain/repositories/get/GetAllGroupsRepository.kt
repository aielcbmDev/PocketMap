package com.charly.domain.repositories.get

import com.charly.domain.model.Group
import kotlinx.coroutines.flow.Flow

interface GetAllGroupsRepository {

    suspend fun execute(): Flow<List<Group>>
}
