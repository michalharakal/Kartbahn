package org.kartbahn.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

internal actual fun dispatcher(): CoroutineDispatcher = Dispatchers.Main
