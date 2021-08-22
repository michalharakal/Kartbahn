package org.kartbahn.android.di

import org.kartbahn.presentation.RoadsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { RoadsViewModel() }
}