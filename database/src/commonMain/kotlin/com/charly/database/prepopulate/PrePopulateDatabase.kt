package com.charly.database.prepopulate

import com.charly.OpenClassForMocking

@OpenClassForMocking
class PrePopulateDatabase(
    private val isDatabaseCreated: Boolean,
    private val prePopulateTablesLazy: Lazy<PrePopulateTables>
) {

    suspend fun execute() {
        if (isDatabaseCreated) return
        prePopulateTablesLazy.value.execute()
    }
}
