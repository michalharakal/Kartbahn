package org.kartbahn.di

import org.kartbahn.api.KartbahnApi
import org.kartbahn.common.KartbahnRepository
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun initKoin(appDeclaration: Module): KoinApplication =
    startKoin {
        modules(
            appDeclaration,
//            platformModule,
            coreModule
        )
    }

private val coreModule = module {
    single {
        KartbahnApi("https://verkehr.autobahn.de/o/autobahn")
    }
    single { KartbahnRepository() }
}

expect val platformModule: Module

