package com.charly.core.repositories.database.prepopulate

import com.charly.database.OpenClassForMocking
import com.charly.database.utils.PrePopulateTables
import com.charly.domain.repositories.database.prepopulate.PrePopulateDatabaseRepository

@OpenClassForMocking
class PrePopulateDatabaseRepositoryImpl(
    private val isDatabaseCreated: Boolean,
    private val prePopulateTablesLazy: Lazy<PrePopulateTables>
) : PrePopulateDatabaseRepository {

    override suspend fun execute() {
        if (isDatabaseCreated) return
        prePopulateTablesLazy.value.execute()
    }
}
