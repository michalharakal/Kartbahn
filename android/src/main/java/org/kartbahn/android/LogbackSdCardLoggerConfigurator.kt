package org.kartbahn.android

import android.content.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.android.LogcatAppender;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;


class LogbackSdCardLoggerConfigurator() {
    fun startLogging(context: Context) {
        // reset the default context (which may already have been initialized)
        // since we want to reconfigure
        if (LoggerFactory.getILoggerFactory() !is LoggerContext) {
            return
        }
        val lc: LoggerContext = LoggerFactory.getILoggerFactory() as LoggerContext
        lc.stop()

        // setup LogcatAppender
        val logcatEncoder = PatternLayoutEncoder()
        logcatEncoder.context = lc
        logcatEncoder.pattern = "[%thread] %msg%n"
        logcatEncoder.start()
        val logcatAppender = LogcatAppender()
        logcatAppender.context = lc
        logcatAppender.encoder = logcatEncoder
        logcatAppender.start()

        // add the newly created appender to the root logger;
        // qualify Logger to disambiguate from org.slf4j.Logger
        val rootLogger: ch.qos.logback.classic.Logger =
            LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME) as ch.qos.logback.classic.Logger
        rootLogger.addAppender(logcatAppender)
        val logDir: File = context.getExternalFilesDir(EXTERNAL_ROOT_DIR)
            ?: return
        val rollingFileAppender: RollingFileAppender<ILoggingEvent> =
            RollingFileAppender()
        rollingFileAppender.isAppend = true
        rollingFileAppender.context = lc

        // Set the periodicity to day. That is, if a log event occurs after the
        // day-boundary, the log file will rollover.
        val PERIODICITY = "yyyy-MM-dd"
        val rollingPolicy: SizeAndTimeBasedRollingPolicy<ILoggingEvent> =
            SizeAndTimeBasedRollingPolicy()
        rollingPolicy.setFileNamePattern(
            logDir.getAbsolutePath()
                .toString() + "/log.%d{" + PERIODICITY + "}.%i.log.gz"
        )
        rollingPolicy.setMaxFileSize(FileSize(TEN_MB))
        rollingPolicy.setMaxHistory(5) // no more than 5 rollover files (delete oldest)
        rollingPolicy.setParent(rollingFileAppender)
        rollingPolicy.setContext(lc)
        rollingPolicy.start()
        rollingFileAppender.setRollingPolicy(rollingPolicy)
        val encoder = PatternLayoutEncoder()
        encoder.setPattern("%date{ISO8601} [%thread] %-5level %logger{36} - %msg%n")
        encoder.setContext(lc)
        encoder.start()
        rollingFileAppender.setEncoder(encoder)
        rollingFileAppender.start()
        rootLogger.addAppender(rollingFileAppender)
    }

    companion object {
        /**
         * null value represents ROOT dir [Context.getExternalFilesDir]
         */
        private val EXTERNAL_ROOT_DIR: String? = null
        private val TEN_MB: Long = FileSize.MB_COEFFICIENT * 10L
    }
}
