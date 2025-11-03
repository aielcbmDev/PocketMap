package com.charly.domain.repositories.database.get

import com.charly.domain.model.database.Group
import kotlinx.coroutines.flow.Flow

interface GetAllGroupsRepository {

    suspend fun execute(): Flow<List<Group>>
}
