package com.charly.database.repositories.prepopulate

import com.charly.database.OpenClassForMocking
import com.charly.domain.repositories.prepopulate.PrePopulateDatabaseRepository

@OpenClassForMocking
class PrePopulateDatabaseRepositoryImpl(
    private val isDatabaseCreated: Boolean,
    private val prePopulateTablesLazy: Lazy<PrePopulateTables>
): PrePopulateDatabaseRepository {

    override suspend fun execute() {
        if (isDatabaseCreated) return
        prePopulateTablesLazy.value.execute()
    }
}
