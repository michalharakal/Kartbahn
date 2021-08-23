package org.kartbahn.presentation.model

data class RoadViewModelData(val name: String, val warnings: List<WarningViewModelData>)

data class WarningViewModelData(val name: String)

data class RoadsViewModelData(val roads: List<RoadViewModelData>)

fun createDefaultRoadsViewModelData() = RoadsViewModelData(
    listOf(
        RoadViewModelData("A4", listOf(WarningViewModelData("Baustele"))),
        RoadViewModelData("A4", emptyList())
    )
)
