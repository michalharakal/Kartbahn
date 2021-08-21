package org.kartbahn.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

import android.content.Context

lateinit var appContext: Context

internal actual fun dispatcher(): CoroutineDispatcher = Dispatchers.Main
