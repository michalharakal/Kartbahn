package org.kartbahn.api

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class LocalDateTimeHolder(val timestamp:LocalDateTime)