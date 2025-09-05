package charly.baquero.pocketmap.data.database.prepopulate

import charly.baquero.pocketmap.OpenClassForMocking

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
