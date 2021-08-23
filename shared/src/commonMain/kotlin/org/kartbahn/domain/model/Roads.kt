package org.kartbahn.domain.model

data class RoadWork(val name: String)

data class Warning(val name: String)

data class ElectricChargingStation(val name: String)

data class Road(
    val name: String,
    val roadWork: List<RoadWork>,
    val warnings: List<Warning>,
    val electricChargingStations: List<ElectricChargingStation>
)

data class Roads(val roads: List<Road>)