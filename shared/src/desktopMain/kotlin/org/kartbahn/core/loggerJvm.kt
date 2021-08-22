package org.kartbahn.core


import org.slf4j.LoggerFactory

private val LOGGER = LoggerFactory.getLogger(LoggerFactory::class.java)

actual fun logger(level: LogLevel, tag: String, message: String) {
    LOGGER.debug("$tag===$message")
}

actual fun logger(level: LogLevel, tag: String, message: String, error: Throwable) {
    LOGGER.error("$tag===$message", error)
}