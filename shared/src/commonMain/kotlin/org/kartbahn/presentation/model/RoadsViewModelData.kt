package org.kartbahn.presentation.model

data class RoadViewModelData(val name: String, val warningsCount:Int)

data class WarningViewModelData(val name: String)

data class RoadsViewModelData(val roads: List<RoadViewModelData>)

fun createDefaultRoadsViewModelData() = RoadsViewModelData(
    listOf(
        RoadViewModelData("A4", 0),
        RoadViewModelData("A4", 2)
    )
)
