package org.kartbahn.core


actual fun logger(level: LogLevel, tag: String, message: String) {
    print("$tag $message")
}

actual fun logger(level: LogLevel, tag: String, message: String, error: Throwable) {
    print("$tag $message")
}