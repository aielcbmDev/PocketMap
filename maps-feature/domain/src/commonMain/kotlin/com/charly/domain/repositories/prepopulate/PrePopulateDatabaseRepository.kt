package com.charly.domain.repositories.prepopulate

interface PrePopulateDatabaseRepository {

    suspend fun execute()
}
