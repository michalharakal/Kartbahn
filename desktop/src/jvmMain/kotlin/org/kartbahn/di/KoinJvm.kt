package org.kartbahn.di

import org.kartbahn.presentation.RoadsViewModel
import org.koin.dsl.module

val appModule = module {
    single { RoadsViewModel() }
}