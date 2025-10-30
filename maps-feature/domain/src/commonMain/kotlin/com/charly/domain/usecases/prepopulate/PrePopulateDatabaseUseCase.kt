package com.charly.domain.usecases.prepopulate

import com.charly.domain.OpenClassForMocking
import com.charly.domain.repositories.prepopulate.PrePopulateDatabaseRepository

@OpenClassForMocking
class PrePopulateDatabaseUseCase(
    private val prePopulateDatabaseRepository: PrePopulateDatabaseRepository
) {

    suspend fun execute() {
        prePopulateDatabaseRepository.execute()
    }
}
