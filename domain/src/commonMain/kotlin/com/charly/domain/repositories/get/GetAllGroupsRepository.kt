package com.charly.domain.repositories.get

import com.charly.domain.model.Group

interface GetAllGroupsRepository {

    suspend fun execute(): List<Group>
}
