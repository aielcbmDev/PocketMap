package com.charly.startup.domain

import com.charly.database.repositories.prepopulate.PrePopulateDatabaseRepository
import com.charly.startup.OpenClassForMocking

@OpenClassForMocking
class PrePopulateDatabaseUseCase(
    private val prePopulateDatabaseRepository: PrePopulateDatabaseRepository
) {

    suspend fun execute() {
        prePopulateDatabaseRepository.execute()
    }
}
