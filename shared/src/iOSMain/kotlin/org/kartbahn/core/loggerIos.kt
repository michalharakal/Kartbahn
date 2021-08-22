package org.kartbahn.core

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSThread

private const val CALL_STACK_INDEX = 8

private object IosLogger {

    private val dateFormatter = NSDateFormatter().apply {
        dateFormat = "MM-dd HH:mm:ss.SSS"
    }

    private val tagMap: HashMap<LogLevel, String> = hashMapOf(
        LogLevel.DEBUG to "💚 DEBUG",
        LogLevel.INFO to "💙 INFO",
        LogLevel.ERROR to "❤️ ERROR",
        LogLevel.WARN to "💞 WARN"
    )

    fun performLog(priority: LogLevel, tag: String?, throwable: Throwable?, message: String?) {
        println(buildLog(priority, tag, message, throwable))
    }

    fun setDateFormatterString(formatter: String) {
        dateFormatter.dateFormat = formatter
    }

    private fun getCurrentTime() = dateFormatter.stringFromDate(NSDate())

    private fun mainThread() = if (NSThread.isMainThread()) {"MainThread"} else {"Thread"}

    private fun buildLog(priority: LogLevel, tag: String?, message: String?, throwable: Throwable?): String {
        return "${getCurrentTime()} ${mainThread()} ${tagMap[priority]} ${tag ?: ""} - $message? ${getThrowable(throwable)}}"
    }

    private fun getThrowable(throwable: Throwable?): String {
        throwable?.let {
            return throwable.toString()
        }
        return ""
    }
}

actual fun logger(level: LogLevel, tag: String, message: String) {
    IosLogger.performLog(level, tag, null, message)
}

actual fun logger(level: LogLevel, tag: String, message: String, error: Throwable) {
    IosLogger.performLog(level, tag, error, message)
}
