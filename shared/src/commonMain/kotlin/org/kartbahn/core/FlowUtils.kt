package org.kartbahn.core

import io.ktor.utils.io.core.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


fun <T> Flow<T>.asCommonFlow(): CFlow<T> = CFlow(this)

class CFlow<T>(private val origin: Flow<T>) : Flow<T> by origin {
    fun watch(block: (T) -> Unit): Closeable {
        val job = Job(/*ConferenceService.coroutineContext[Job]*/)

        onEach {
            block(it)
        }.launchIn(CoroutineScope(dispatcher() + job))

        return object : Closeable {
            override fun close() {
                job.cancel()
            }
        }
    }
}