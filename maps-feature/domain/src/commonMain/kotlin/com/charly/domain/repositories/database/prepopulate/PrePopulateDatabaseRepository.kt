package com.charly.domain.repositories.database.prepopulate

interface PrePopulateDatabaseRepository {

    suspend fun execute()
}
