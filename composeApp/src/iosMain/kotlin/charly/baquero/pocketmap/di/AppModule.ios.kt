package charly.baquero.pocketmap.di

import charly.baquero.pocketmap.data.database.DatabaseFactory
import charly.baquero.pocketmap.database.IOSDatabaseFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<DatabaseFactory> { IOSDatabaseFactory() }
    }
