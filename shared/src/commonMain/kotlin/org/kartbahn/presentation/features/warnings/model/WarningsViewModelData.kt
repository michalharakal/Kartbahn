package org.kartbahn.presentation.features.warnings.model

import kotlinx.datetime.LocalDateTime

data class WarningViewModelData(
    val warningId: String,
    val title: String,
    val subtitle: String,
    val start: LocalDateTime
)

data class WarningsViewModelData(
    val roadId: String,
    val warnings: List<WarningViewModelData>
)
