package org.kartbahn.common

import co.touchlab.stately.ensureNeverFrozen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import org.kartbahn.core.LogLevel
import org.kartbahn.core.MainScope
import org.kartbahn.core.logger

actual open class CommonViewModel actual constructor() {
    actual val clientScope: CoroutineScope = MainScope(Dispatchers.Main)

    protected actual open fun onCleared() {
    }
}