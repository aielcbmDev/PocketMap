package charly.baquero.pocketmap.domain

import charly.baquero.pocketmap.OpenClassForMocking
import charly.baquero.pocketmap.data.database.prepopulate.PrePopulateDatabase

@OpenClassForMocking
class PrePopulateDatabaseUseCase(
    private val prePopulateDatabase: PrePopulateDatabase
) {

    suspend fun execute() {
        prePopulateDatabase.execute()
    }
}
