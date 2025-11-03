package com.charly.domain.usecases.database.prepopulate

import com.charly.domain.OpenClassForMocking
import com.charly.domain.repositories.database.prepopulate.PrePopulateDatabaseRepository

@OpenClassForMocking
class PrePopulateDatabaseUseCase(
    private val prePopulateDatabaseRepository: PrePopulateDatabaseRepository
) {

    suspend fun execute() {
        prePopulateDatabaseRepository.execute()
    }
}
