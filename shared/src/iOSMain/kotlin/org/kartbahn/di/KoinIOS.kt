package org.kartbahn.di

import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.dsl.module

fun initKoinIos(

): KoinApplication = initKoin(
    module {
        single { { println("Starting from iOS") } }
    }
)

actual val platformModule: Module = module {
	single { { println("Starting from iOS") } }
}
