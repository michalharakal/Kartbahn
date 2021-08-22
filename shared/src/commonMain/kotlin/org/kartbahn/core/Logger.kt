package org.kartbahn.core


sealed class LogLevel {
    object DEBUG : LogLevel()
    object INFO : LogLevel()
    object WARN : LogLevel()
    object ERROR : LogLevel()
}

expect fun logger(level: LogLevel, tag: String, message: String)

expect fun logger(level: LogLevel, tag: String, message: String, error: Throwable)
