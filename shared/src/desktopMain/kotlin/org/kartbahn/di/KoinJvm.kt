package org.kartbahn.di

import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    // add platform specific caching and logging here
}
