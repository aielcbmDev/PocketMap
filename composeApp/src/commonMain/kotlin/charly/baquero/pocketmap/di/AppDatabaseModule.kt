package charly.baquero.pocketmap.di

import androidx.room.RoomDatabase
import charly.baquero.pocketmap.data.database.PocketMapDatabase
import charly.baquero.pocketmap.data.database.model.groups.GroupDao
import charly.baquero.pocketmap.data.database.model.locations.LocationDao
import charly.baquero.pocketmap.data.database.model.membership.MembershipDao
import charly.baquero.pocketmap.data.database.prepopulate.PrePopulateDatabase
import charly.baquero.pocketmap.data.database.prepopulate.PrePopulateTables
import charly.baquero.pocketmap.utils.AssetFileProvider
import charly.baquero.pocketmap.utils.getRoomDatabase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appDatabaseModule = module {
    factory<PocketMapDatabase> {
        get<RoomDatabase.Builder<PocketMapDatabase>>().getRoomDatabase()
    }

    single<LocationDao> {
        get<PocketMapDatabase>().getLocationDao()
    }

    single<GroupDao> {
        get<PocketMapDatabase>().getGroupDao()
    }

    single<MembershipDao> {
        get<PocketMapDatabase>().getMembershipDao()
    }

    single<AssetFileProvider> {
        AssetFileProvider()
    }

    single<PrePopulateTables> {
        PrePopulateTables(
            pocketMapDatabase = get(),
            locationDao = get(),
            groupDao = get(),
            membershipDao = get(),
            assetFileProvider = get()
        )
    }
    single<PrePopulateDatabase> {
        PrePopulateDatabase(
            get(named("isDatabaseCreated")),
            lazy { get() }
        )
    }
}
