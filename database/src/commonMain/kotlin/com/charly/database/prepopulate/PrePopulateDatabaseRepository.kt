package com.charly.database.prepopulate

import com.charly.database.OpenClassForMocking

@OpenClassForMocking
class PrePopulateDatabaseRepository(
    private val isDatabaseCreated: Boolean,
    private val prePopulateTablesLazy: Lazy<PrePopulateTables>
) {

    suspend fun execute() {
        if (isDatabaseCreated) return
        prePopulateTablesLazy.value.execute()
    }
}
