package org.kartbahn.domain.model

import kotlinx.datetime.LocalDateTime

data class RoadWork(val name: String)

data class Warning(
    val warningId: String,
    val title: String,
    val subtitle: String,
    val start: LocalDateTime
)

data class ElectricChargingStation(val name: String)

data class Road(
    val name: String,
    val roadWork: List<RoadWork>,
    val warnings: List<Warning>,
    val electricChargingStations: List<ElectricChargingStation>
)

data class Roads(val roads: List<Road>)