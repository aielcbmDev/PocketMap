package com.charly.domain.usecases.prepopulate

import com.charly.domain.repositories.prepopulate.PrePopulateDatabaseRepository

class PrePopulateDatabaseUseCase(
    private val prePopulateDatabaseRepository: PrePopulateDatabaseRepository
) {

    suspend fun execute() {
        prePopulateDatabaseRepository.execute()
    }
}
