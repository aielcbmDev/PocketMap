package charly.baquero.pocketmap.domain

import charly.baquero.pocketmap.OpenClassForMocking
import com.charly.database.repositories.prepopulate.PrePopulateDatabaseRepository

@OpenClassForMocking
class PrePopulateDatabaseUseCase(
    private val prePopulateDatabaseRepository: PrePopulateDatabaseRepository
) {

    suspend fun execute() {
        prePopulateDatabaseRepository.execute()
    }
}
